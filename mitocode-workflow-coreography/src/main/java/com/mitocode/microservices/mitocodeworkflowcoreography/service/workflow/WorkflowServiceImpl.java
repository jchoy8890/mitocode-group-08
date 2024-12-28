package com.mitocode.microservices.mitocodeworkflowcoreography.service.workflow;


import com.mitocode.microservices.mitocodeworkflowcoreography.model.entity.OrderEntity;
import com.mitocode.microservices.mitocodeworkflowcoreography.service.activity.ActivityService;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Slf4j
public class WorkflowServiceImpl implements WorkflowService {

    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(30))// 30 45 120 200 400
            .setMaximumAttempts(5)
            .setBackoffCoefficient(2)
            .build();

    private final ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(retryOptions)
            .build();

    private final ActivityService activityService = Workflow.newActivityStub(ActivityService.class, activityOptions);

    private final Saga.Options sagaOptions = new Saga.Options.Builder()
            .setParallelCompensation(true).build();

    private final Saga saga = new Saga(sagaOptions);

    private boolean createdOrder = false;
    private boolean updatedOrder = false;
    private boolean updatedProduct = false;
    private boolean acceptedOrder = false;

    @Override
    public void signalCreateOrder(String orderId) {

        try {
            activityService.createOrder(orderId);

            saga.addCompensation(activityService::cancelOrder, orderId);

            createdOrder = true;

            Workflow.await(() -> createdOrder);
            Workflow.await(() -> updatedOrder);
            Workflow.await(() -> updatedProduct);
            Workflow.await(() -> acceptedOrder);

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            log.info("Compensating the process");
            saga.compensate();
            completeWorkflow();
        }
    }

    @Override
    public void signalUpdateOrder(OrderEntity orderEntity) {
        if (createdOrder) {
            try {
                activityService.updateOrder(orderEntity);
                saga.addCompensation(() -> activityService.cancelOrder(orderEntity.getOrderId()));
                updatedOrder = true;
            } catch (Exception e) {
                log.error("Exception: " + e.getMessage());
                log.info("Compensating the process");
                saga.compensate();
                completeWorkflow();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @Override
    public void signalUpdateProduct(String productId, Integer quantity) {
        if (updatedOrder) {
            try {
                activityService.updateProduct(productId, quantity);
                saga.addCompensation(() -> activityService.reverseProduct(productId, quantity));
                updatedProduct = true;
            } catch (Exception e) {
                log.error("Exception: " + e.getMessage());
                log.info("Compensating the process");
                saga.compensate();
                completeWorkflow();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @Override
    public void signalAcceptOrder(String orderId) {
        if (updatedProduct) {
            try {
                activityService.confirmOrder(orderId);
                acceptedOrder = true;
//                saga.addCompensation(activityService::cancelOrder, orderId);
            } catch (Exception e) {
                log.error("Exception: " + e.getMessage());
                log.info("Compensating the process");
                saga.compensate();
                completeWorkflow();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error");
        }
    }

    @Override
    public void signalCancelOrder(String orderId) {
//        activityService.cancelOrder(orderId);
        saga.compensate();
        completeWorkflow();
    }

    private void completeWorkflow() {
        createdOrder = true;
        updatedOrder = true;
        updatedProduct = true;
        acceptedOrder = true;
    }

}
