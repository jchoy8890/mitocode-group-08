package com.mitocode.microservices.authenticationserveroauth2.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class UserDTO {

    private String id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private Collection<GrantedAuthority> grantedAuthorities;

}
