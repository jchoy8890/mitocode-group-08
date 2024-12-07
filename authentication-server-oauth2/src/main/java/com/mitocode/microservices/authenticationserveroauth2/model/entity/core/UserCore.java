package com.mitocode.microservices.authenticationserveroauth2.model.entity.core;

import com.mitocode.microservices.authenticationserveroauth2.model.dto.UserDTO;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class UserCore extends User {

    private final String id;
    private final String name;

    public UserCore(UserDTO userDTO) {
        super(userDTO.getUsername(), userDTO.getPassword(), userDTO.getGrantedAuthorities());
        this.id = userDTO.getId();
        this.name = userDTO.getName();
    }
}
