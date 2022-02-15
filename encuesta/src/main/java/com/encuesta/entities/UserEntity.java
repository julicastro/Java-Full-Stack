package com.encuesta.entities;

import lombok.Data;
import javax.persistence.*;

@Entity(name="users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false, length = 225)
    private String email;

    @Column(nullable = false, length = 225)
    private String encryptedPassword;





}
