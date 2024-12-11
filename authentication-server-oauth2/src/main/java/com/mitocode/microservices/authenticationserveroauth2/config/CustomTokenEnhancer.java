package com.mitocode.microservices.authenticationserveroauth2.config;

import com.mitocode.microservices.authenticationserveroauth2.model.dto.UserDTO;
import com.mitocode.microservices.authenticationserveroauth2.model.entity.UserEntity;
import com.mitocode.microservices.authenticationserveroauth2.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        String username = oAuth2Authentication.getName();

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            log.error("Username: " + username + " no registrado");
            throw new UsernameNotFoundException("Username: " + username + " no registrado");
        }

        UserDTO userDTO = UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .grantedAuthorities(
                        Arrays.stream(userEntity.getRoles())
                                .map(SimpleGrantedAuthority::new).collect(Collectors.toList())

                )
                .build();

        Map<String, Object> customClaims = new HashMap<>();

//        customClaims.put("email", userDTO.getEmail());
//        customClaims.put("username", userDTO.getUsername());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(oAuth2AccessToken);
        customAccessToken.setAdditionalInformation(customClaims);

        return customAccessToken;

    }
}
