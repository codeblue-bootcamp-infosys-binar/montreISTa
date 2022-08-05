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
    private Long photo_id;

    private String photo_name;
    private String photo_url;
    private Product product;

    public Photo convertToEntity(){
        return Photo.builder()
                .photoId(this.photo_id)
                .photoURL(this.photo_url)
                .photoName(this.photo_name)
                .product(this.product)
                .build();
    }
}
