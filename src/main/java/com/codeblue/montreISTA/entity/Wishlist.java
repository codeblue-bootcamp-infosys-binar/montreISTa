package com.codeblue.montreISTA.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long wishlistId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wishlist", cascade = CascadeType.ALL)
    private List<Buyers> buyers;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "wishlist",cascade = CascadeType.ALL)
    private List<Product> products;

}
