package com.mitocode.microservices.product_command_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mitocode.microservices")
public class ProductCommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCommandServiceApplication.class, args);
	}

}
