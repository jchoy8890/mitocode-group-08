package com.mitocode.microservices.mitocodeworkfloworchestration.service.workflow;

import com.mitocode.microservices.mitocodeworkfloworchestration.OrderEntity;
import com.mitocode.microservices.mitocodeworkfloworchestration.request.OrderRequest;
import com.mitocode.microservices.mitocodeworkfloworchestration.service.activity.ActivityService;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class WorkflowServiceImpl implements WorkflowService {

    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(15))
            .setMaximumAttempts(3)
            .setBackoffCoefficient(1)
            .build();

    private final ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(retryOptions)
            .build();

    private final ActivityService activityService = Workflow.newActivityStub(ActivityService.class, activityOptions);

    private final Saga.Options sagaOptions = new Saga.Options.Builder()
            .setParallelCompensation(true).build();
    private final Saga saga = new Saga(sagaOptions);


    @Override
    public void signalCreateOrder(OrderRequest orderRequest) {

        try {

            OrderEntity orderEntity = OrderEntity.builder()
                    .orderId(orderRequest.getOrderId())
                    .quantity(orderRequest.getQuantity())
                    .productId(orderRequest.getProductId())
                    .build();

            activityService.createOrder(orderEntity.getOrderId());

            saga.addCompensation(activityService::cancelOrder, orderEntity.getOrderId());

            activityService.updateOrder(orderEntity);

            saga.addCompensation(activityService::cancelOrder, orderEntity.getOrderId());

            activityService.updateProduct(orderRequest.getProductId(), orderRequest.getQuantity());

            saga.addCompensation(activityService::reverseProduct, orderRequest.getProductId(), orderRequest.getQuantity());

            // Exception

            activityService.confirmOrder(orderRequest.getOrderId());

        } catch (Exception e) {

            log.error("Exception: " + e.getMessage());
            log.info("Compensating the process");
            saga.compensate();

        }
    }

}
