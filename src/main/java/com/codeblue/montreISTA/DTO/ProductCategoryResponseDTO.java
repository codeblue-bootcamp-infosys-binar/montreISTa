package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductCategoryResponseDTO {
    private Long product_category_id;
    private String product_name;
    private Integer price;
    private List<PhotoProductDTO> produk_photos;
    private String store_name;
    private String store_photo;
    private String seller_name;
    private String seller_username;
    private String store_address;
    private String category_name;
    private String product_description;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
}
