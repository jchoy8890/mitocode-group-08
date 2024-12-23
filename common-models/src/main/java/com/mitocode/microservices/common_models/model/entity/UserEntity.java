package com.mitocode.microservices.common_models.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@Document(collection = "user")
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String[] roles;

}
