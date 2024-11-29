package com.mitocode.microservices.productservice.service.repository;

import com.mitocode.microservices.productservice.model.entity.ProductPostgresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPostgreSQLRepository extends JpaRepository<ProductPostgresEntity, String> {
}
