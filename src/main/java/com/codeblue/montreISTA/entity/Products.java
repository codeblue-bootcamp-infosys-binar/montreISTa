package com.codeblue.montreISTA.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private Sellers seller;

    @Column(name="product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

}

