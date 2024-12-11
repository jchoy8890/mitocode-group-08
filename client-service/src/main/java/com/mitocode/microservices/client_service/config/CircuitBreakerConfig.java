package com.mitocode.microservices.client_service.config;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

//@Configuration
public class CircuitBreakerConfig {

//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> customCircuitBreakerFactory() {
//
//        return factory -> factory.configureDefault(
//                id -> new Resilience4JConfigBuilder(id).circuitBreakerConfig(
//                                io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
//
//                                        /**
//                                         * Sliding Window Size: 100 últimos requests
//                                         * Failure Rate Threshold: 50%
//                                         * Wait Duration in Open State: 60 segundos
//                                         * Permitted Number of Calls in Half Open State: 10 requests
//                                         * */
//
//                                        // Ratio de error
//                                        .slidingWindowSize(20) // 100
//                                        .failureRateThreshold(20) // 50
//                                        .waitDurationInOpenState(Duration.ofSeconds(20)) // 60
//                                        .permittedNumberOfCallsInHalfOpenState(9) // 10
//
//                                        // Ratio de respuestas lentas
//                                        .slowCallDurationThreshold(Duration.ofMillis(2000))
//                                        .slowCallRateThreshold(50)
//                                        .build()
//                        )
//
//                        .timeLimiterConfig(TimeLimiterConfig.custom()
//                                .timeoutDuration(Duration.ofMillis(2500L))
//                                .build())
//                        .build());
//    }
}
