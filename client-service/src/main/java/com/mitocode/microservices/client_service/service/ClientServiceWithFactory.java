package com.mitocode.microservices.client_service.service;

import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import com.mitocode.microservices.client_service.proxy.openfeign.ProductServiceFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceWithFactory {


    private final CircuitBreakerFactory circuitBreakerFactory;
    private final ProductServiceFeign productServiceFeign;


    public List<ProductDTO> getAllProducts() {

        return circuitBreakerFactory.create("mitocode")
                .run(() -> productServiceFeign.getAllProducts(),
                        (e) -> customSimpleFallback(e)
                );
    }

    public List<ProductDTO> getAllProductsWithParameter(boolean flag) {
        return
                circuitBreakerFactory.create("mitocode-slow")
                        .run(() -> productServiceFeign.getAllProductsWithFlagForSlow(flag),
                                (e) -> customFlagFallback(flag, e));
    }

    public List<ProductDTO> customFlagFallback(boolean flag, Throwable e) {
        log.error("With flag " + flag + " : " + e.getMessage());
        List<ProductDTO> lstProducts = new ArrayList<>();
        lstProducts.add(ProductDTO.builder()
                .port(9999)
                .price(100L)
                .productId("P99999")
                .productName("Product Fake")
                .productType("Fake")

                .build());
        return lstProducts;
    }

    public List<ProductDTO> customSimpleFallback(Throwable e) {
        log.error(e.getMessage());
        List<ProductDTO> lstProducts = new ArrayList<>();
        lstProducts.add(ProductDTO.builder()
                .port(9999)
                .price(100L)
                .productId("P99999")
                .productName("Product Fake")
                .productType("Fake")

                .build());
        return lstProducts;
    }


}
