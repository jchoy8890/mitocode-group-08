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
        return Arrays.asList("headerValue","headerKey", "flag", "value1");
    }

    @Override
    public GatewayFilter apply(CustomConfiguration config) {

        System.out.println("..::..Custom filter started..::..");

        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {

            exchange.getResponse().getHeaders().add(config.getHeaderKey(), config.getHeaderValue());
            exchange.getResponse().getCookies().add("responseCookie", ResponseCookie
                    .from(config.headerKey, config.getHeaderValue()).build());

            System.out.println(String.format("Parámetros del Custom Filter: %s %s %s %s", config.getHeaderKey(),
                    config.getHeaderValue(), config.getValue1(), config.isFlag()));

            System.out.println("..::..Custom filter finished..::..");

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
        private String value1;
        private boolean flag;

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

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
