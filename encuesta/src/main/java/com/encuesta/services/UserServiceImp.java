package com.encuesta.services;

import com.encuesta.entities.UserEntity;
import com.encuesta.models.requests.UserRegisterRequestModel;
import com.encuesta.repositories.IUserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements iUserService {

    // @Autowired
    IUserRepository userRepository;
    /*
     * inyectamos la clase mediante el constructor y no con @autowired
     * para hacerlo mas facil a la hora de hacer los tests
     */
     public UserServiceImp(IUserRepository userRepository){
         this.userRepository = userRepository;
         /* es lo mismo q autowired */
     }

    @Override
    public UserEntity getUser(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserEntity createUser(UserRegisterRequestModel user) {
        UserEntity userEntity = new UserEntity();

        // copiamos propieades de user a userEntity
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword(user.getPassword());

        return userRepository.save(userEntity);
    }

}
