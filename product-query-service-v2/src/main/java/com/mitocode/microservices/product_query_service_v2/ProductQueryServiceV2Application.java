package com.mitocode.microservices.product_query_service_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mitocode.microservices")
public class ProductQueryServiceV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProductQueryServiceV2Application.class, args);
	}

}
