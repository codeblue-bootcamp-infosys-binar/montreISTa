package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;

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


    private String storeName;


    private String storePhoto;


    private String storeAddress;

}
