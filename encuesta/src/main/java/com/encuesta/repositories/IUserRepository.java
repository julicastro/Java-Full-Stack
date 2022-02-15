package com.encuesta.repositories;

import com.encuesta.entities.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    
    public UserEntity findByEmail(String email);

    public UserEntity findById(long id);
}
