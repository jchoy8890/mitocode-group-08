package com.mitocode.microservices.authentication_server_jwt.model.request;

public record UserRegister(
        String id,
        String name,
        String lastname,
        String email,
        String username,
        String password,
        String[] roles
) {
}