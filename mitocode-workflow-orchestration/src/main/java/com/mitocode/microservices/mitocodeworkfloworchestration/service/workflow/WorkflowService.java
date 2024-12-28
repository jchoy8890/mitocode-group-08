package com.mitocode.microservices.mitocodeworkfloworchestration.service.workflow;

import com.mitocode.microservices.mitocodeworkfloworchestration.request.OrderRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkflowService {

    // Initial step
    @WorkflowMethod
    void signalCreateOrder(OrderRequest orderRequest);


}
