package com.mitocode.microservices.authentication_server_jwt.expose.web;

import com.mitocode.microservices.authentication_server_jwt.model.request.UserCredentials;
import com.mitocode.microservices.authentication_server_jwt.model.request.UserRegister;
import com.mitocode.microservices.authentication_server_jwt.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegister userRegister){
        return ResponseEntity.ok(securityService.register(userRegister));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserCredentials userCredentials){
        return ResponseEntity.ok(securityService.authenticate(userCredentials));
    }


}
