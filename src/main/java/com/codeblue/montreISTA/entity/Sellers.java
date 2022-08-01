package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Sellers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sellerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users userId;

    @NotBlank(message = "name must not be blank")
    private String storeName;

    @NotBlank(message = "store photo must not be blank")
    private String storePhoto;

    @NotBlank(message = "store addre must not be blank")
    private String storeAddress;

}