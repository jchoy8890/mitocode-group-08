package com.mitocode.microservices.authentication_server_jwt.service.repository;


import com.mitocode.microservices.authentication_server_jwt.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
}
