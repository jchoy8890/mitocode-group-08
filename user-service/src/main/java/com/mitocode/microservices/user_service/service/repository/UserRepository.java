package com.mitocode.microservices.user_service.service.repository;

import com.mitocode.microservices.common_models.model.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "user")
public interface UserRepository extends PagingAndSortingRepository<UserEntity, String> {


//    @RestResource(exported = false)
//    void deleteById(String id);


    @RestResource(exported = true)
    UserEntity getAllById(String id);

    @RestResource(path = "correito")
    UserEntity getAllByEmail(String email);

    @RestResource(path = "apellido")
    List<UserEntity> getAllByLastnameContains(String lastname);

    @RestResource(path = "multiple")
//    List<UserEntity> getAllByLastnameAndEmail(String lastname, String email);
        //List<UserEntity> getAllByLastnameOrEmail(String lastname, String email);
    List<UserEntity> getAllByLastnameContainsAndEmailContains(String lastname, String email);

}
