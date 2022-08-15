package com.codeblue.montreISTA.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoResponseDTO {
    private Long photo_id;
    private String photo_url;
    private Long product_id;
    private String product_name;
    private String description;
    private Integer price;
    private String seller_name;
    private String store_name;
    private String store_photo;
    private String store_address;

    @Override
    public String toString() {
        return "PhotoResponseDTO{" +
                "photo_id=" + photo_id +
                ", photo_url='" + photo_url + '\'' +
                ", product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", seller_name='" + seller_name + '\'' +
                ", store_name='" + store_name + '\'' +
                ", store_photo='" + store_photo + '\'' +
                ", store_address='" + store_address + '\'' +
                '}';
    }
}
