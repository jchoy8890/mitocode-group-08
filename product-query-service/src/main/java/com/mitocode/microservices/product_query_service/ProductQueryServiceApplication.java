package com.mitocode.microservices.product_query_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mitocode.microservices")
public class ProductQueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductQueryServiceApplication.class, args);
	}

}
