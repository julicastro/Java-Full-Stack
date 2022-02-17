package com.encuesta.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.encuesta.annotations.UniqueEmail;
import com.encuesta.entities.UserEntity;
import com.encuesta.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator <UniqueEmail, String>{

    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserEntity user = userRepository.findByEmail(value);
        if(user == null){
            return true;
        }
        return false;
    }
    
}
