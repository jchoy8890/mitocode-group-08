package com.mitocode.microservices.client_service.proxy.openfeign;

import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.common_models.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cloud-gateway")
public interface CloudGatewayFeign {


    @GetMapping("/api/user/api/mitocode/user")
    UserDTO getAllUsers();

    @GetMapping("/api/product/product")
    List<ProductDTO> getAllProducts();

    @PostMapping("/api/product/saveProduct")
    ProductDTO createProduct(@RequestBody ProductDTO productDTO);


    @GetMapping("/api/product/product/slow/{flag}")
    List<ProductDTO> getAllProductsWithFlagForSlow(@PathVariable("flag") boolean flag);

    @GetMapping("/api/product/products")
    List<ProductDTO> getAllProductsWithParameter(
            @RequestParam("flag") boolean flag,
            @RequestHeader("appCallerName") String appCallerName);

}
