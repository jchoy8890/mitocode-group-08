package com.mitocode.microservices.client_service.service;

import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import com.mitocode.microservices.client_service.model.dto.UserDTO;
import com.mitocode.microservices.client_service.proxy.openfeign.ProductServiceFeign;
import com.mitocode.microservices.client_service.proxy.openfeign.UserServiceFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {


    private final ProductServiceFeign productServiceFeign;
    private final UserServiceFeign userServiceFeign;
    private final CircuitBreakerFactory circuitBreakerFactory;


    @CircuitBreaker(name = "mitocode-product", fallbackMethod = "customSimpleFallback")
    public List<ProductDTO> getAllProductsAnnotation() {
        return productServiceFeign.getAllProducts();
    }


    @CircuitBreaker(name = "mitocode-product", fallbackMethod = "customFlagFallback")
    public List<ProductDTO> getAllProductsWithParameterCB(boolean flag) {
        return productServiceFeign
                .getAllProductsWithParameter(flag, "client-service");

    }


    /**  ========= Implementación sin Timelimiter ==============
     *
     * @CircuitBreaker(name = "mitocode-product", fallbackMethod = "customFlagFallback")
     * public List<ProductDTO> getAllProductsWithParameterAnnotation(boolean flag) {
     * return productServiceFeign.getAllProductsWithFlagForSlow(flag);
     * }
     *
     **/


    /**
     * ========= Implementación con Timelimiter ==============
     */
//    @Retry(name = "default")
//    @CircuitBreaker(name = "mitocode-product")
//    @TimeLimiter(name = "mitocode-product-tl")
//    public CompletableFuture<List<ProductDTO>> getAllProductsWithParameterAnnotation(boolean flag) {
//        return
//                CompletableFuture.supplyAsync(
//                        () -> productServiceFeign.getAllProductsWithFlagForSlow(flag)
//                );
//    }
    public List<ProductDTO> getAllProducts() {

        return circuitBreakerFactory.create("mitocode")
                .run(() -> productServiceFeign.getAllProducts(),
                        (e) -> customSimpleFallback(e)
                );
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

    public UserDTO getAllUsers() {
        return userServiceFeign.getAllUsers();
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        return productServiceFeign.createProduct(productDTO);
    }

    public List<ProductDTO> getAllProductsWithParameter(boolean flag) {
        return
                circuitBreakerFactory.create("mitocode-slow")
                        .run(() -> productServiceFeign.getAllProductsWithFlagForSlow(flag),
                                (e) -> customFlagFallback(flag, e));
    }


    /**
     * ========= Implementación sin Timelimiter ==============
     * <p>
     * <p>
     * <p>
     */
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
