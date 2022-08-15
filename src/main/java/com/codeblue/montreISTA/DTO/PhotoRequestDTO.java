package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoRequestDTO {

    private String photo_url;
    private Long product_id;


    public Photo convertToEntity(Product product){
        return Photo.builder()
                .photoURL(this.photo_url)
                .product(product)
                .build();
    }
}
