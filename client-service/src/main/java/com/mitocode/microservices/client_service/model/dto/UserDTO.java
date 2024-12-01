package com.mitocode.microservices.client_service.model.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserDTO {

    private List<UserResponse> content;

}
