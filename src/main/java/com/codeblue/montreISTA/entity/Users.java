package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @NotEmpty(message = "Name may not be empty")
    private String name;

    private String username;
    private String email;
    private String password;
    private String phone;
    private String photo;
    private String address;

}
