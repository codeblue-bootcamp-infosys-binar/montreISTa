package com.codeblue.montreISTA.entity;


import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.service.PhotoServiceImp;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "productCategoryId")
public class ProductCategory extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public ProductCategoryResponseDTO convertToResponse(ProductToProductCategoryDTO product, CategoryResponseDTO category){

        return ProductCategoryResponseDTO.builder()
                .product_category_id(this.getProductCategoryId())
                .product(product)
                .category(category)
                .build();
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productCategoryId=" + productCategoryId +
                ", product=" + product +
                ", category=" + category +
                '}';
    }
}

