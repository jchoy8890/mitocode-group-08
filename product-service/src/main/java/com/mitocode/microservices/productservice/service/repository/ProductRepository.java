package com.mitocode.microservices.productservice.service.repository;

import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {


    Optional<ProductEntity> findByProductId(String s);

}


