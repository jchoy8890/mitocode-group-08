package com.mitocode.microservices.productservice;

import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import com.mitocode.microservices.common_models.model.entity.ProductPostgresEntity;
import com.mitocode.microservices.productservice.service.repository.ProductPostgreSQLRepository;
import com.mitocode.microservices.productservice.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan("com.mitocode.microservices.*")
public class ProductServiceApplication implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ProductPostgreSQLRepository productPostgreSQLRepository;


    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        productRepository.deleteAll();

        productRepository.save(ProductEntity.builder().productId("P00001").productName("Microservicios Básico").productType("Curso").price(200L).stock(50).build());

        productRepository.save(ProductEntity.builder().productId("P00002").productName("Microservicios Avanzados").productType("Curso").price(300L).stock(40).build());

        productPostgreSQLRepository.save(ProductPostgresEntity.builder().productId("P00001").productName("Microservicios Básico").productType("Curso").price(200L).stock(50).build());

        productPostgreSQLRepository.save(ProductPostgresEntity.builder().productId("P00002").productName("Microservicios Avanzados").productType("Curso").price(300L).stock(40).build());


    }

}
