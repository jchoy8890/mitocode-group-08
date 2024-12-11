package com.mitocode.microservices.client_service.expose.web;

import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import com.mitocode.microservices.client_service.model.dto.UserDTO;
import com.mitocode.microservices.client_service.service.ClientService;
import com.mitocode.microservices.client_service.service.ClientServiceWithFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientServiceWithFactory clientServiceWithFactory;

    @GetMapping("/product")
    public List<ProductDTO> getAllProducts() {
        return clientService.getAllProductsAnnotation();
    }

    @GetMapping("/user")
    public UserDTO getAllUsers() {
        return clientService.getAllUsers();
    }

    @PostMapping("/product")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return clientService.saveProduct(productDTO);
    }

//    @GetMapping("/product/{flagSlow}")
//    public CompletableFuture<List<ProductDTO>> getProductFlag(@PathVariable("flagSlow") boolean flagSlow) {
//        return clientService.getAllProductsWithParameterAnnotation(flagSlow);
//    }

    @GetMapping("/product/{flagSlow}")
    public List<ProductDTO> getProductFlag(@PathVariable("flagSlow") boolean flagSlow) {
        return clientService.getAllProductsWithParameterCB(flagSlow);
    }

    @GetMapping("/products/{flagSlow}")
    public List<ProductDTO> getProductsFlag(@PathVariable("flagSlow") boolean flagSlow) {
        return clientService.getAllProductsWithParameterCB(flagSlow);
    }

}
