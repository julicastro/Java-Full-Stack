package com.encuesta.services;

import com.encuesta.entities.UserEntity;
import com.encuesta.models.requests.UserRegisterRequestModel;

public interface iUserService {

    public UserEntity getUser(String email);

    public UserEntity createUser(UserRegisterRequestModel user);
}



