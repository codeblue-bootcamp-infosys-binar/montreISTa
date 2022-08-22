package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.DTO.ProductToProductCategoryDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "productId")
public class Product extends AuditEntity {
    //product id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //seller id
    @ManyToOne
    @NotNull
    @JoinColumn(name="seller_id")
    private Seller seller;

    //product name
    @Column(name="product_name")
    @NotNull
    private String productName;

    //description
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description", columnDefinition = "TEXT")
    @NotNull
    private String description;

    //price
    @Column(name = "price")
    @NotNull
    private Integer price;

    //list of photos
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product",
            fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product",
            fetch = FetchType.LAZY)
    private List<ProductCategory> categories;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product",
            fetch = FetchType.LAZY)
    private List<Cart> carts;

    public ProductResponseDTO convertToResponse(List<PhotoProductDTO> photoDTO, List<String> categories){

        return ProductResponseDTO.builder()
                .productId(this.getId())
                .sellerId(this.seller.getSellerId())
                .storeName(this.seller.getStoreName())
                .storePhoto(this.seller.getStorePhoto())
                .productName(this.productName)
                .description(this.description)
                .price(this.price)
                .photos(photoDTO)
                .categories(categories)
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
    public ProductToProductCategoryDTO convertToProductCategory(){
        return ProductToProductCategoryDTO.builder()
                .productId(this.getId())
                .sellerId(this.getSeller().getSellerId())
                .productName(this.getProductName())
                .storeName(this.getSeller().getStoreName())
                .storePhoto(this.getSeller().getStorePhoto())
                .description(this.getDescription())
                .price(this.getPrice())
                .build();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", seller=" + seller +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", photos=" + photos +
                ", categories=" + categories +
                ", carts=" + carts +
                '}';
    }

}

