package com.mitocode.microservices.clientserviceribbon.model.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserDTO {

    private List<UserResponse> content;

}
