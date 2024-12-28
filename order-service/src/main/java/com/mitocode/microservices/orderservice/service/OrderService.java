package com.mitocode.microservices.orderservice.service;

import com.mitocode.microservices.orderservice.model.entity.OrderEntity;
import com.mitocode.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity createOrder(String orderId) {
        OrderEntity orderEntity = OrderEntity.builder()
                .creationDate(System.currentTimeMillis())
                .orderId(orderId)
                .status(0)
                .build();

        return orderRepository.save(orderEntity);
    }

    public OrderEntity cancelOrder(String orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Id not found"));

        orderEntity.setUpdateDate(System.currentTimeMillis());
        orderEntity.setStatus(3);
        orderRepository.save(orderEntity);

        return orderEntity;
    }

    public OrderEntity updateOrder(OrderEntity orderEntity) {

        OrderEntity entity = orderRepository.findById(orderEntity.getOrderId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Id not found"));

        entity.setStatus(2);
        entity.setQuantity(orderEntity.getQuantity());
        entity.setProductId(orderEntity.getProductId());
        entity.setUpdateDate(System.currentTimeMillis());
        orderRepository.save(entity);
        return entity;

    }

    public String acceptOrder(String orderId) {
        OrderEntity entity = orderRepository.findById(orderId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Id not found"));

        entity.setStatus(1);
        entity.setUpdateDate(System.currentTimeMillis());

        orderRepository.save(entity);

        return "Ok";

    }
}
