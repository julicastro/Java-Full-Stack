package com.encuesta.services;

import com.encuesta.entities.UserEntity;
import com.encuesta.models.requests.UserRegisterRequestModel;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService{

    public UserEntity getUser(String email);

    public UserEntity createUser(UserRegisterRequestModel user);

     UserDetails loadUserByUsername(String email);


}

