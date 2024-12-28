package com.mitocode.microservices.orderservice.controller;

import com.mitocode.microservices.orderservice.model.entity.OrderEntity;
import com.mitocode.microservices.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/create/{orderId}")
    public ResponseEntity<OrderEntity> createOrder(@PathVariable("orderId") String orderId) {
        log.info("[OrderController] - Creating order: " + orderId);
        return ResponseEntity.ok(orderService.createOrder(orderId));
    }

    @PutMapping("/order")
    public ResponseEntity<OrderEntity> updateOrder(@RequestBody OrderEntity orderEntity) {
        log.info("[OrderController] - Updating order: " + orderEntity.getOrderId());
        return ResponseEntity.ok(orderService.updateOrder(orderEntity));
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<String> acceptOrder(@PathVariable("orderId") String orderId) {
        log.info("[OrderController] - Updating order: " + orderId);
        return ResponseEntity.ok(orderService.acceptOrder(orderId));
    }

    @GetMapping("/order/cancel/{orderId}")
    public ResponseEntity<OrderEntity> cancelOrder(@PathVariable("orderId") String orderId) {
        log.info("[OrderController] - Canceling order: " + orderId);
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

}

