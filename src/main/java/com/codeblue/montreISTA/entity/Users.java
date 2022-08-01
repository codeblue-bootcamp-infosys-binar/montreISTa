package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Getter
@Setter
@Entity
@Table(name = "users")
public class Users extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @NotBlank(message = "name may not be blank")
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    @NotBlank(message = "username may not be blank")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "email may not be blank")
    @Email
    private String email;

    @NotBlank(message = "password may not be blank")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "phone number may not be blank")
    private String phone;

    @NotBlank(message = "photo url may not be blank")
    private String photo;

    @NotBlank(message = "address may not be blank")
    private String address;

}
