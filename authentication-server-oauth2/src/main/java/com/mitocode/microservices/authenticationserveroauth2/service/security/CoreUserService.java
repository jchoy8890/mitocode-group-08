package com.mitocode.microservices.authenticationserveroauth2.service.security;

import com.mitocode.microservices.authenticationserveroauth2.model.dto.UserDTO;
import com.mitocode.microservices.authenticationserveroauth2.model.entity.UserEntity;
import com.mitocode.microservices.authenticationserveroauth2.model.entity.core.UserCore;
import com.mitocode.microservices.authenticationserveroauth2.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoreUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            log.error("Username: "  + username + " no registrado");
            throw new UsernameNotFoundException("Username: "  + username + " no registrado");
        }

        UserDTO userDTO = UserDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .grantedAuthorities(
                        Arrays.stream(userEntity.getRoles())
                                .map(SimpleGrantedAuthority::new).collect(Collectors.toList())

                )
                .build();

        return new UserCore(userDTO);

    }
}
