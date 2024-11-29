package com.mitocode.microservices.clientserviceribbon.expose.web;

import com.mitocode.microservices.clientserviceribbon.model.dto.ProductDTO;
import com.mitocode.microservices.clientserviceribbon.model.dto.UserDTO;
import com.mitocode.microservices.clientserviceribbon.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
