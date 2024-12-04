package com.mitocode.microservices.client_service.proxy.openfeign;


import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceFeign {

    @GetMapping("/product")
    List<ProductDTO> getAllProducts();

    @PostMapping("/saveProduct")
    ProductDTO createProduct(@RequestBody ProductDTO productDTO);


    @GetMapping("/product/slow/{flag}")
    List<ProductDTO> getAllProductsWithFlagForSlow(@PathVariable("flag") boolean flag);

    @GetMapping("/products")
    List<ProductDTO> getAllProductsWithParameter(
            @RequestParam("flag") boolean flag,
            @RequestHeader("appCallerName") String appCallerName);

}
