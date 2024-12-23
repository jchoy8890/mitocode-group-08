package com.mitocode.microservices.client_service.proxy.openfeign;


import com.mitocode.microservices.common_models.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service")
public interface UserServiceFeign {

    @GetMapping("/api/mitocode/user")
    UserDTO getAllUsers();
}
