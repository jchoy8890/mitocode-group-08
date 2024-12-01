package com.mitocode.microservices.client_service.expose.web;

import com.mitocode.microservices.client_service.model.dto.ProductDTO;
import com.mitocode.microservices.client_service.model.dto.UserDTO;
import com.mitocode.microservices.client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/product")
    public List<ProductDTO> getAllProducts() {
        return clientService.getAllProducts();
    }

    @GetMapping("/user")
    public UserDTO getAllUsers() {
        return clientService.getAllUsers();
    }

    @PostMapping("/product")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return clientService.saveProduct(productDTO);
    }

    @GetMapping("/product/{flagSlow}")
    public List<ProductDTO> addProduct(@PathVariable("flagSlow") boolean flagSlow) {
        return clientService.getAllProductsWithParameter(flagSlow);
    }

}
