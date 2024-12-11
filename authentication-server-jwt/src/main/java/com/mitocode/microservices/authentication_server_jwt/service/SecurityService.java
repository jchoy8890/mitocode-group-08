package com.mitocode.microservices.authentication_server_jwt.service;

import com.mitocode.microservices.authentication_server_jwt.config.JwtService;
import com.mitocode.microservices.authentication_server_jwt.model.entity.UserEntity;
import com.mitocode.microservices.authentication_server_jwt.model.request.UserCredentials;
import com.mitocode.microservices.authentication_server_jwt.model.request.UserRegister;
import com.mitocode.microservices.authentication_server_jwt.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public String register(UserRegister userRegister) {
        UserEntity userEntity = UserEntity.builder()
                .username(userRegister.username())
                .password(passwordEncoder.encode(userRegister.password()))
                .email(userRegister.email())
                .name(userRegister.name())
                .roles(userRegister.roles())
                .lastname(userRegister.lastname())
                .build();

        userRepository.save(userEntity);
        return jwtService.generateToken(userEntity);
    }

    public String authenticate(UserCredentials userCredentials) {


        log.info(userCredentials.toString());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentials.username(), userCredentials.password()));

        UserEntity userEntity = userRepository.findByUsername(userCredentials.username())
                .orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("Usuario %s no encontrado en la BD",
                    userCredentials.username()));
        }

        return jwtService.generateToken(userEntity);


    }
}
