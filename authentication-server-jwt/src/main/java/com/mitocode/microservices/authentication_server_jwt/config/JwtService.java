package com.mitocode.microservices.authentication_server_jwt.config;


import com.mitocode.microservices.authentication_server_jwt.model.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {


    @Value("${mitocode.security.key:mitocode}")
    public String mitocodeKey;

    public String generateToken(UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetails;

        Map<String, Object> claims = new HashMap<>();
//        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
//        claims.put("password", user.getPassword());
        claims.put("roles", user.getRoles());


        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getKey())
                .compact();

    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(mitocodeKey.getBytes()));
    }

}
