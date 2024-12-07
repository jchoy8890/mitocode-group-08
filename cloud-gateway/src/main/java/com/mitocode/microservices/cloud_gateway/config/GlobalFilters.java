package com.mitocode.microservices.cloud_gateway.config;

//import lombok.extern.slf4j.Slf4j;
//import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

//@Slf4j
//@Slf4j
@Component
public class GlobalFilters implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("..::..Global filter started..::..");
        System.out.println("Prefilter");
        System.out.println("Request to: " + exchange.getRequest().getURI());
        Long startTime = System.currentTimeMillis();

        String appCallerNameStr = "cloud-gateway";

        // Adding headers to Request
//        exchange.getRequest().mutate().header("appCallerName","cloud-gateway");
//        exchange.getRequest().mutate().header("appCallerName", "cloud-gateway").build();
        Optional<String> appCallerName = Optional.ofNullable(exchange.getRequest()
                .getHeaders().getFirst("appCallerName"));

        if (appCallerName.isEmpty()) {
            exchange.getRequest().mutate().header("appCallerName", appCallerNameStr);
        }


        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println("Postfilter");
            System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + " ms");

            // Adding headers to Response
            exchange.getResponse().getHeaders().add("appCallerName", appCallerNameStr);
            exchange.getResponse().getCookies().add("responseCookie", ResponseCookie
                    .from("appCallerName", appCallerNameStr).build());


            System.out.println("..::..Global filter finished..::..");

        }));


    }
}
