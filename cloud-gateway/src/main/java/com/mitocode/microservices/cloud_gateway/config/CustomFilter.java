package com.mitocode.microservices.cloud_gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.CustomConfiguration> {

    public CustomFilter() {
        super(CustomConfiguration.class);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("headerValue","headerKey");
    }

    @Override
    public GatewayFilter apply(CustomConfiguration config) {


        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {

            exchange.getResponse().getHeaders().add(config.getHeaderKey(), config.getHeaderValue());
            exchange.getResponse().getCookies().add("responseCookie", ResponseCookie
                    .from(config.headerKey, config.getHeaderValue()).build());

        })));
    }

    @Override
    public String name() {
        return "MitocodeCustomFilter";
    }


    //    @Data
    public static class CustomConfiguration {

        private String headerKey;
        private String headerValue;

        public String getHeaderKey() {
            return headerKey;
        }

        public void setHeaderKey(String headerKey) {
            this.headerKey = headerKey;
        }

        public String getHeaderValue() {
            return headerValue;
        }

        public void setHeaderValue(String headerValue) {
            this.headerValue = headerValue;
        }
    }
}
