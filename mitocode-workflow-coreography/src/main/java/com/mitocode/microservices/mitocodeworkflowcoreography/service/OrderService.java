package com.mitocode.microservices.mitocodeworkflowcoreography.service;

import com.mitocode.microservices.mitocodeworkflowcoreography.config.TemporalConfig;
import com.mitocode.microservices.mitocodeworkflowcoreography.model.entity.OrderEntity;
import com.mitocode.microservices.mitocodeworkflowcoreography.request.OrderRequest;
import com.mitocode.microservices.mitocodeworkflowcoreography.service.workflow.WorkflowService;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final WorkflowClient workflowClient;


    public String createOrder(String orderId) {

        String workflowId = UUID.randomUUID().toString().substring(0, 6);
        WorkflowService workflowService = createWorkflowConnection(workflowId);
        WorkflowClient.start(() -> workflowService.signalCreateOrder(orderId));

        return workflowId;

    }

    public String updateOrder(OrderRequest orderRequest) {

        WorkflowService workflowService = workflowClient.newWorkflowStub(WorkflowService.class,
                "Order_" + orderRequest.getWorkflowId());

        OrderEntity orderEntity = OrderEntity.builder()
                .orderId(orderRequest.getOrderId())
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .build();

        workflowService.signalUpdateOrder(orderEntity);

        return "Order updated";

    }

    public String updateProduct(String productId, Integer quantity, String workflowId) {

        WorkflowService workflowService = workflowClient.newWorkflowStub(WorkflowService.class,
                "Order_" + workflowId);

        workflowService.signalUpdateProduct(productId, quantity);

        return "Product updated";
    }

    public String acceptOrder(String orderId, String workflowId) {

        WorkflowService workflowService = workflowClient.newWorkflowStub(WorkflowService.class,
                "Order_" + workflowId);

        workflowService.signalAcceptOrder(orderId);

        return "Order confirmed";
    }

    public String cancelProcess(String orderId, String workflowId) {

        WorkflowService workflowService = workflowClient.newWorkflowStub(WorkflowService.class,
                "Order_" + workflowId);


        workflowService.signalCancelOrder(orderId);

        return "Process cancelled";
    }

    private WorkflowService createWorkflowConnection(String workflowId) {
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue(TemporalConfig.QUEUE_NAME)
                .setWorkflowId("Order_" + workflowId)
                .build();
        return workflowClient.newWorkflowStub(WorkflowService.class, workflowOptions);
    }


}
