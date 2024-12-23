package com.mitocode.microserice.audit_service.service;

import com.mitocode.microservices.common_models.model.entity.AuditEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<AuditEntity, String> {
}
