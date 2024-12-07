package com.mitocode.microservices.authentication_client_oauth2.expose.web;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @GetMapping("/public")
    public String publicResource() {
        return "Public Resource";
    }

    @GetMapping("/protected")
    public String protectedResource(@AuthenticationPrincipal OAuth2User user) {

        if(user == null) {
            throw new RuntimeException("User not found");
        }

        String name = user.getAttribute("name");

        String email = user.getAttribute("email");

        user.getAuthorities().forEach(System.out::println);
        System.out.println(email);

        return String.format("Bienvenido %s", name);
    }


}
