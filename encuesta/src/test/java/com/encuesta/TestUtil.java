package com.encuesta;

import java.util.Random;

import com.encuesta.models.requests.UserRegisterRequestModel;

public class TestUtil {

    public static UserRegisterRequestModel createValidUser(){
        UserRegisterRequestModel user = new UserRegisterRequestModel();
        user.setEmail(generateRandomString(8) + "@gamil.com");
        user.setName(generateRandomString(8));
        user.setPassword(generateRandomString(8));
        return user;
    }

    public static String generateRandomString(int len) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
}
