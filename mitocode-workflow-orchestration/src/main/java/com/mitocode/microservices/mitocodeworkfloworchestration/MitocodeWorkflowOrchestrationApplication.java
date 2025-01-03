package com.mitocode.microservices.mitocodeworkfloworchestration;

import com.mitocode.microservices.mitocodeworkfloworchestration.config.TemporalConfig;
import com.mitocode.microservices.mitocodeworkfloworchestration.service.activity.ActivityService;
import com.mitocode.microservices.mitocodeworkfloworchestration.service.workflow.WorkflowServiceImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringBootApplication
public class MitocodeWorkflowOrchestrationApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext appContext = SpringApplication.run(MitocodeWorkflowOrchestrationApplication.class, args);

		WorkerFactory workerFactory = appContext.getBean(WorkerFactory.class);
		ActivityService activityService = appContext.getBean(ActivityService.class);

		Worker worker = workerFactory.newWorker(TemporalConfig.QUEUE_NAME);
		worker.registerWorkflowImplementationTypes(WorkflowServiceImpl.class);
		worker.registerActivitiesImplementations(activityService);

		workerFactory.start();
	}

}
