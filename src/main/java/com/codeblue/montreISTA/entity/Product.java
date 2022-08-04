package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

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
public class Product extends AuditEntity{

    //product id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

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

    public ProductResponseDTO convertToResponse(List<PhotoProductDTO> photoDTO){
        return ProductResponseDTO.builder()
                .productId(this.productId)
                .sellerId(this.seller.getSellerId())
                .storeName(this.seller.getStoreName())
                .storePhoto(this.seller.getStorePhoto())
                .productName(this.productName)
                .description(this.description)
                .price(this.price)
                .photos(photoDTO)
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
}