package com.codeblue.montreISTA.entity;


import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryResponseDTO;
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

    public ProductCategoryResponseDTO convertToResponse(List<PhotoProductDTO> photo){

        return ProductCategoryResponseDTO.builder()
                .product_category_id(this.getProductCategoryId())
                .product_name(this.getProduct().getProductName())
                .price(this.getProduct().getPrice())
                .produk_photos(photo)
                .store_name(this.getProduct().getSeller().getStoreName())
                .store_photo(this.getProduct().getSeller().getStorePhoto())
                .seller_name(this.getProduct().getSeller().getUserId().getName())
                .seller_username(this.getProduct().getSeller().getUserId().getUsername())
                .store_address(this.getProduct().getSeller().getStoreAddress())
                .category_name(this.getCategory().getName())
                .product_description(this.getProduct().getDescription())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
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

