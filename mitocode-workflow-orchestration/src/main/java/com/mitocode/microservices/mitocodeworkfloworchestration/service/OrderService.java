package com.mitocode.microservices.mitocodeworkfloworchestration.service;

import com.mitocode.microservices.mitocodeworkfloworchestration.config.TemporalConfig;
import com.mitocode.microservices.mitocodeworkfloworchestration.request.OrderRequest;
import com.mitocode.microservices.mitocodeworkfloworchestration.service.workflow.WorkflowService;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final WorkflowClient workflowClient;

    public String createOrder(OrderRequest orderRequest) {

        String workflowId = UUID.randomUUID().toString().substring(0, 6);
        WorkflowService workflowService = createWorkflowConnection(workflowId);
        WorkflowClient.start(() -> workflowService.signalCreateOrder(orderRequest));

        return workflowId;

    }

    private WorkflowService createWorkflowConnection(String workflowId) {
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue(TemporalConfig.QUEUE_NAME)
                .setWorkflowId("Order_" + workflowId)
                .build();
        return workflowClient.newWorkflowStub(WorkflowService.class, workflowOptions);
    }


}
