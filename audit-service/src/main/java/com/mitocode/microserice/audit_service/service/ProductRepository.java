package com.mitocode.microserice.audit_service.service;

import com.mitocode.microservices.common_models.model.entity.AuditEntity;
import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, String> {
}
