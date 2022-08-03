package com.codeblue.montreISTA.entity;

import DTO.PhotoPostDTO;
import DTO.PhotoResponseDTO;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "photos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Photo extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @NotEmpty
    private String photoName;


    @NotBlank
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    private String photoURL;

    @ManyToOne
    @JoinColumn(name = "product_id")
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
    public PhotoPostDTO convertToPost(){
        return PhotoPostDTO.builder()
                .photo_id(this.photoId)
                .photo_name(this.photoName)
                .photo_url(this.photoURL)
                .product_id(this.getProduct().getProductId())
                .build();
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", photoName='" + photoName + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", product=" + product +
                '}';
    }
}

