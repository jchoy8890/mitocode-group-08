package com.mitocode.microservices.orderservice.repository;


import com.mitocode.microservices.orderservice.model.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {
}
