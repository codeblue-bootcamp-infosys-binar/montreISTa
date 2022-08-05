package com.codeblue.montreISTA.entity;

<<<<<<< HEAD
import com.codeblue.montreISTA.DTO.PhotoProductDTO;
=======
import lombok.AllArgsConstructor;
import com.codeblue.montreISTA.DTO.PhotoPostDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
>>>>>>> 9296f8e3460a6f033b517fb67a30996f9b115b40
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
import com.codeblue.montreISTA.DTO.PhotoPostDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
=======
>>>>>>> 9296f8e3460a6f033b517fb67a30996f9b115b40
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photos")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "photoId")
@Builder
public class Photo extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @NotEmpty
    private String photoName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String photoURL;

    @ManyToOne(targetEntity = Product.class)
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

    public PhotoProductDTO convertToProduct(){
        return PhotoProductDTO.builder()
                .photo_id(this.photoId)
                .photo_name(this.photoName)
                .photo_url(this.photoURL)
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

