package com.mitocode.microservices.authenticationserveroauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class AuthenticationServerOauth2Application implements CommandLineRunner {

	private final BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServerOauth2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("******************************");
		log.info("jchoy: " + encoder.encode("jchoy"));
		log.info("mitocode: " +encoder.encode("mitocode"));
		log.info("******************************");
	}
}
