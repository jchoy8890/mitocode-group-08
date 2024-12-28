package com.mitocode.microservices.mitocodeworkflowcoreography.config;

import com.mitocode.microservices.mitocodeworkflowcoreography.proxy.OrderFeign;
import com.mitocode.microservices.mitocodeworkflowcoreography.proxy.ProductFeign;
import com.mitocode.microservices.mitocodeworkflowcoreography.service.activity.ActivityService;
import com.mitocode.microservices.mitocodeworkflowcoreography.service.activity.ActivityServiceImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TemporalConfig {

    private final ProductFeign productFeign;
    private final OrderFeign orderFeign;

    @Value("${temporal.mitocode.hostname:127.0.0.1:7233}")
    private String HOSTNAME;

    @Value("${temporal.mitocode.namespace:default}")
    private String NAMESPACE;

    public static String QUEUE_NAME = "mitocode";

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs.newInstance(WorkflowServiceStubsOptions.newBuilder()
                .setTarget(HOSTNAME).build());
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
        return WorkflowClient.newInstance(workflowServiceStubs, WorkflowClientOptions.newBuilder()
                .setNamespace(NAMESPACE).build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public ActivityService signUpActivity(){
        return new ActivityServiceImpl(productFeign, orderFeign);
    }

}
