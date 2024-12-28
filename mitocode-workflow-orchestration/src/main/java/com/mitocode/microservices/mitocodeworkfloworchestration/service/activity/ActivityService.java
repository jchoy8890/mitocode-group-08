package com.mitocode.microservices.mitocodeworkfloworchestration.service.activity;

import com.mitocode.microservices.mitocodeworkfloworchestration.OrderEntity;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ActivityService {

    @ActivityMethod
    void createOrder(String orderId);

    @ActivityMethod
    void updateOrder(OrderEntity orderEntity);

    @ActivityMethod
    void updateProduct(String productId, Integer quantity);

    @ActivityMethod
    void confirmOrder(String orderId);

    // Compensation methods
    @ActivityMethod
    void cancelOrder(String orderId);

    @ActivityMethod
    void reverseProduct(String productId, Integer quantity);

}