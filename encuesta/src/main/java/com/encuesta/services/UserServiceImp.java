package com.encuesta.services;

import java.util.ArrayList;

import com.encuesta.entities.UserEntity;
import com.encuesta.models.requests.UserRegisterRequestModel;
import com.encuesta.repositories.IUserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements IUserService {

    // @Autowired
    IUserRepository userRepository;

    // @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     * inyectamos la clase mediante el constructor y no con @autowired
     * para hacerlo mas facil a la hora de hacer los tests
     */
    public UserServiceImp(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        /* es lo mismo q autowired */
    }

    @Override
    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity createUser(UserRegisterRequestModel user) {
        UserEntity userEntity = new UserEntity();

        // copiamos propieades de user a userEntity
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /* metodo de spring security q busca usuario en la base de datos */
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                new ArrayList<>());
    }

}
