package com.mitocode.microservices.clientserviceribbon.service;

import com.mitocode.microservices.clientserviceribbon.model.dto.ProductDTO;
import com.mitocode.microservices.clientserviceribbon.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final RestTemplate restTemplate;

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productLst = restTemplate.getForObject("http://product-service/product", List.class);
        return productLst;
    }

    public UserDTO getAllUsers() {
        UserDTO userLst = restTemplate.getForObject("http://user-service/api/mitocode/user", UserDTO.class);
        log.info("..::..=>" + userLst.toString());
        return userLst;
    }

}
