package com.mitocode.microservices.common_models.model.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserDTO {

    private List<UserResponse> content;

}