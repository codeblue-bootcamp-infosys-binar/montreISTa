package com.codeblue.montreISTA.entity;


import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @NotBlank
    private Buyer buyer;

    //harusny ini list
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotBlank(message= "orders must not be blank")
    private Product product;

}
