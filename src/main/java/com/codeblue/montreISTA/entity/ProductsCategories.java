package com.codeblue.montreISTA.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
public class ProductsCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productCategoryId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;
}

