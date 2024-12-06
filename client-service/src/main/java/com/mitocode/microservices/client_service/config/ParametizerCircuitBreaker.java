package com.mitocode.microservices.client_service.config;


import com.mitocode.microservices.client_service.util.CircuitBreakerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class ParametizerCircuitBreaker {

    private final CircuitBreakerProperties properties;

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> customCircuitBreakerFactory() {

        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id).circuitBreakerConfig(
                                io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                                        .slidingWindowSize(properties.getSlidingWindowSize())
                                        .failureRateThreshold(properties.getFailureRateThreshold())
                                        .waitDurationInOpenState(Duration.ofMillis(properties.getWaitDurationInOpenState()))
                                        .permittedNumberOfCallsInHalfOpenState(properties.getPermittedNumberOfCallsInHalfOpenState())
                                        .slowCallDurationThreshold(Duration.ofMillis(properties.getSlowCallDurationThreshold()))
                                        .slowCallRateThreshold(properties.getSlowCallRateThreshold())
                                        .build()
                        )
                        .build());
    }

}
