package com.mitocode.microservices.clientserviceribbon;

import com.mitocode.microservices.clientserviceribbon.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@RibbonClient(name = "product-service-mitocode", configuration = RibbonConfig.class)
@SpringBootApplication
public class ClientServiceRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceRibbonApplication.class, args);
    }

}
