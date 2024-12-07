package com.mitocode.microservices.authenticationserveroauth2.service.repository;

import com.mitocode.microservices.authenticationserveroauth2.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    UserEntity findByUsername(String username);

}
