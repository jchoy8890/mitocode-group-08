package com.mitocode.microservices.mitocodeworkflowcoreography.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-service")
public interface ProductFeign {

    @PutMapping("/product/reserve/{productId}/{quantity}")
    String reserve(@PathVariable("productId") String productId,
                   @PathVariable("quantity") Integer quantity);

}
