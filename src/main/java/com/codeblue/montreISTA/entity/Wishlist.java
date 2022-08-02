package com.codeblue.montreISTA.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class Wishlist extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @NotBlank
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "productCategory_id")
    @NotBlank(message= "orders must not be blank")
    private ProductCategory productCategory;

}
