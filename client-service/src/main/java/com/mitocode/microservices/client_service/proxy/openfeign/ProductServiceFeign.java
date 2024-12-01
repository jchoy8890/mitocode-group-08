package com.mitocode.microservices.client_service.proxy.openfeign;


import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceFeign {

    @GetMapping("/product")
    List<ProductDTO> getAllProducts();

    @PostMapping("/saveProduct")
    ProductDTO createProduct(@RequestBody ProductDTO productDTO);


    @GetMapping("/product/slow/{flag}")
    List<ProductDTO> getAllProductsWithFlagForSlow(@PathVariable("flag") boolean flag);
}
