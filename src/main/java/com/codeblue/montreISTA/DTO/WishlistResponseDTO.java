package com.codeblue.montreISTA.DTO;


import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponseDTO {

    private Long wishlist_id;
    private Long buyer_id;

    private ProductResponseDTO product;
    private Integer quantity;
    private ZonedDateTime created_at;
    private ZonedDateTime modified_at;


    @Override
    public String toString() {
        return "WishlistResponseDTO{" +
                "wishlist_id=" + wishlist_id +
                ", buyer_id=" + buyer_id +
                ", product_id=" + product +
                ", quantity=" + quantity +
                ", created_at=" + created_at +
                ", modified_at=" + modified_at +
                '}';
    }
}
