package com.mitocode.microserice.audit_service.service;

import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import com.mitocode.microservices.common_models.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
}
