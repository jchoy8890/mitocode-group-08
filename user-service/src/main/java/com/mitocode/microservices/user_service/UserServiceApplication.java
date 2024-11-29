package com.mitocode.microservices.user_service;

import com.mitocode.microservices.user_service.model.entity.UserEntity;
import com.mitocode.microservices.user_service.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {


//    private final UserRepository userRepository;

    @Value("${application.title:mitocode}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("..::.. " + applicationName);
//        userRepository.findAll().forEach(System.out::println);
//        userRepository.save(UserEntity.builder().id("ID02").name("Juanito").build());
//        userRepository.deleteAll();
    }
}
