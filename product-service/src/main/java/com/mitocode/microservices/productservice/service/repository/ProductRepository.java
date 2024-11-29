package com.mitocode.microservices.productservice.service.repository;

import com.mitocode.microservices.productservice.model.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, String> {
}
