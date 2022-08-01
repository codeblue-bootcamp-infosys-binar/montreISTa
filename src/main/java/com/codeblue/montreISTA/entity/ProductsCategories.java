package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "products_categories")
public class ProductsCategories extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoriesId;

//    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Orders.class)
//    private Product product;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Orders.class)
    private Categories categories;


}
