package com.mitocode.microservices.product_command_service.service.repository;

import com.mitocode.microservices.common_models.model.entity.ProductPostgresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPostgreSQLRepository extends JpaRepository<ProductPostgresEntity, String> {
}
