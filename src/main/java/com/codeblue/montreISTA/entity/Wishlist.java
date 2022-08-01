package com.codeblue.montreISTA.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class Wishlist extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long wishlistId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @NotBlank
    private Buyers buyers;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    @NotBlank
//    private Product product;

}
