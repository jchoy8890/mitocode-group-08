package com.mitocode.microservices.common_models.model.dto;

import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String[] roles;


}
