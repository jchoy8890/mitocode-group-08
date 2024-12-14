package com.mitocode.microservices.cloud_gateway_keycloak.util;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class UtilProperties {


    private String resourceId;
    private String principalAttribute;

}
