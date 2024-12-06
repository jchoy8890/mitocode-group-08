package com.mitocode.microservices.client_service.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mitocode.circuit-breaker.common")
public class CircuitBreakerProperties {

    private Integer slidingWindowSize;
    private Integer failureRateThreshold;
    private long waitDurationInOpenState;
    private int permittedNumberOfCallsInHalfOpenState;
    private boolean automaticTransitionFromOpenToHalfOpenEnabled;
    private long slowCallDurationThreshold;
    private Integer slowCallRateThreshold;

}



