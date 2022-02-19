package com.encuesta.models.requests;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.encuesta.annotations.UniqueEmail;

@Data
public class UserRegisterRequestModel {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    @UniqueEmail
    private String email;

    @NotEmpty
    @Size(min=8, max=45)
    private String password;



}
