package com.mitocode.microservices.mitocodeworkfloworchestration.proxy;


import com.mitocode.microservices.mitocodeworkfloworchestration.OrderEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderFeign {

    @GetMapping("/order/create/{orderId}")
    OrderEntity createOrder(@PathVariable("orderId") String orderId);

    @PutMapping("/order")
    OrderEntity updateOrder(@RequestBody OrderEntity orderEntity);

    @PutMapping("/order/{orderId}")
    String acceptOrder(@PathVariable("orderId") String orderId);

    @GetMapping("/order/cancel/{orderId}")
    OrderEntity cancelOrder(@PathVariable("orderId") String orderId);

}
