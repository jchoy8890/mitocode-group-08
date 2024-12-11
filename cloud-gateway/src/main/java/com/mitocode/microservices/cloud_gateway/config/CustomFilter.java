package com.mitocode.microservices.cloud_gateway.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.CustomConfiguration> {

    public CustomFilter() {
        super(CustomConfiguration.class);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("headerValue", "headerKey", "flag", "value1");
    }

    @Override
    public GatewayFilter apply(CustomConfiguration config) {


        return ((exchange, chain) -> {
            log.info("..::..Custom filter started..::..");

            log.info("PRUEBAAAA");


            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                exchange.getResponse().getHeaders().add(config.getHeaderKey(), config.getHeaderValue());
                exchange.getResponse().getCookies().add("responseCookie", ResponseCookie
                        .from(config.headerKey, config.getHeaderValue()).build());

                log.info(String.format("Parámetros del Custom Filter: %s %s %s %s%n", config.getHeaderKey(),
                        config.getHeaderValue(), config.getValue1(), config.isFlag()));

                log.info("..::..Custom filter finished..::..");
            }));

        });
    }

    @Override
    public String name() {
        return "MitocodeCustomFilter";
    }


    @Data
    public static class CustomConfiguration {

        private String headerKey;
        private String headerValue;
        private String value1;
        private boolean flag;

    }
}
