package com.mitocode.microservices.cloud_gateway_keycloak.config.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthConverter authConverter;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        log.info("Validating token");

        return http.authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/product/**").hasRole("maintainer")
                                .pathMatchers("/api/user/**").hasAnyRole("admin", "maintainer")
                                .anyExchange().authenticated())
//                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .oauth2ResourceServer( auth -> auth.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(authConverter)))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

}
