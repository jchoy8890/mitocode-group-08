package com.mitocode.microservices.product_query_service.service.repository;

import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, String> {
}
