package com.mitocode.microservices.client_service.service;

import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import com.mitocode.microservices.client_service.model.dto.UserDTO;
import com.mitocode.microservices.client_service.proxy.openfeign.ProductServiceFeign;
import com.mitocode.microservices.client_service.proxy.openfeign.UserServiceFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {


    private final ProductServiceFeign productServiceFeign;
    private final UserServiceFeign userServiceFeign;
    private final CircuitBreakerFactory circuitBreakerFactory;


    public List<ProductDTO> getAllProducts() {


        return circuitBreakerFactory.create("mitocode")
                .run(()-> productServiceFeign.getAllProducts());


        /**
         * Sliding Window Size: 100 últimos requests
         * Failure Rate Threshold: 50%
         * Wait Duration in Open State: 60 segundos
         * Permitted Number of Calls in Half Open State: 10 requests
         * */


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
                                .run(()->productServiceFeign.getAllProductsWithFlagForSlow(flag));
    }
}
