package com.mitocode.microservices.mitocodeworkfloworchestration.service.activity;


import com.mitocode.microservices.mitocodeworkfloworchestration.OrderEntity;
import com.mitocode.microservices.mitocodeworkfloworchestration.proxy.OrderFeign;
import com.mitocode.microservices.mitocodeworkfloworchestration.proxy.ProductFeign;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ProductFeign productFeign;
    private final OrderFeign orderFeign;

    public ActivityServiceImpl(ProductFeign productFeign, OrderFeign orderFeign) {
        this.productFeign = productFeign;
        this.orderFeign = orderFeign;
    }

    @Override
    public void createOrder(String orderId) {
        log.info("Creating order");
        orderFeign.createOrder(orderId);
        log.info("Order created");
    }

    @Override
    public void updateOrder(OrderEntity orderEntity) {
        log.info("Updating order");
        orderFeign.updateOrder(orderEntity);
        log.info("Order updated");
    }

    @Override
    public void updateProduct(String productId, Integer quantity) {
        log.info("Updating product");
        productFeign.reserve(productId, quantity);
        log.info("Product updated");
    }

    @Override
    public void confirmOrder(String orderId) {
        log.info("Accepting order");
        orderFeign.acceptOrder(orderId);
        log.info("Order accepted");
    }

    @Override
    public void cancelOrder(String orderId) {
        log.info("Cancelling order");
        orderFeign.cancelOrder(orderId);
        log.info("Order cancelled");
    }

    @Override
    public void reverseProduct(String productId, Integer quantity) {
        log.info("Reversing product");
        productFeign.reserve(productId, quantity * -1);
        log.info("Product reverted");
    }
}
