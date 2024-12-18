package com.mitocode.microservices.client_service.service;

import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import com.mitocode.microservices.client_service.model.dto.UserDTO;
import com.mitocode.microservices.client_service.proxy.openfeign.CloudGatewayFeign;
import com.mitocode.microservices.client_service.proxy.openfeign.ProductServiceFeign;
import com.mitocode.microservices.client_service.proxy.openfeign.UserServiceFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {


//    private final ProductServiceFeign productServiceFeign;
//    private final UserServiceFeign userServiceFeign;
    private final CloudGatewayFeign cloudGatewayFeign;

    @CircuitBreaker(name = "mitocode-product", fallbackMethod = "customSimpleFallback")
    public List<ProductDTO> getAllProductsAnnotation() {
        return cloudGatewayFeign.getAllProducts();
    }


    @Retry(name = "retry-product")
    @CircuitBreaker(name = "mitocode-product", fallbackMethod = "customFlagFallback")
    public List<ProductDTO> getAllProductsWithParameterCB(boolean flag) {
        return cloudGatewayFeign
                .getAllProductsWithParameter(flag, "client-service");


//        List<ProductDTO> productLst = restTemplate.getForObject("http://product-service/product/" + flag, List.class);
//        return productLst;


    }


    /**
     * ========= Implementación sin Timelimiter ==============
     **/

    @CircuitBreaker(name = "mitocode-product", fallbackMethod = "customFlagFallback")
    public List<ProductDTO> getAllProductsWithParameterAnnotation(boolean flag) {
        return cloudGatewayFeign.getAllProductsWithFlagForSlow(flag);
    }


    /**
     * ========= Implementación con Timelimiter ==============
     */

/*
    @CircuitBreaker(name = "mitocode-product")
    @TimeLimiter(name = "mitocode-product-tl")
    public CompletableFuture<List<ProductDTO>> getAllProductsWithParameterAnnotation(boolean flag) {
        return
                CompletableFuture.supplyAsync(
                        () -> productServiceFeign.getAllProductsWithFlagForSlow(flag)
                );
    }
*/
    public UserDTO getAllUsers() {
        return cloudGatewayFeign.getAllUsers();
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        return cloudGatewayFeign.createProduct(productDTO);
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
        throw new RuntimeException("With flag " + flag + " : " + e.getMessage());
//        return lstProducts;
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

    /**
     * ========= Implementación con Timelimiter ==============
     **/
//    public CompletableFuture<List<ProductDTO>> customFlagFallback(boolean flag, Throwable e) {
//        log.error("With flag " + flag + " : " + e.getMessage());
//        List<ProductDTO> lstProducts = new ArrayList<>();
//        lstProducts.add(ProductDTO.builder()
//                .port(9999)
//                .price(100L)
//                .productId("P99999")
//                .productName("Product Fake")
//                .productType("Fake")
//
//                .build());
//        return CompletableFuture.supplyAsync(() -> lstProducts);
//    }
}
