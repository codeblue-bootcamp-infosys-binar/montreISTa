package com.codeblue.montreISTA.entity;

import DTO.PhotoResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "photos")
@Builder
public class Photo extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @NotBlank
    private String photoName;

    @NotBlank
    @Lob
    @Column(columnDefinition = "TEXT")
    private String photoURL;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotBlank
    private Product product;

    public PhotoResponseDTO convertToResponse(){
        return PhotoResponseDTO.builder()
                .photo_id(this.photoId)
                .photo_name(this.photoName)
                .photo_url(this.photoURL)
                .product_id(this.getProduct().getProductId())
                .product_name(this.getProduct().getProductName())
                .description(this.getProduct().getDescription())
                .price(this.getProduct().getPrice())
                .seller_name(this.getProduct().getSeller().getUserId().getName())
                .store_name(this.getProduct().getSeller().getStoreName())
                .store_photo(this.getProduct().getSeller().getStorePhoto())
                .store_address(this.getProduct().getSeller().getStoreAddress())
                .build();
    }
}

