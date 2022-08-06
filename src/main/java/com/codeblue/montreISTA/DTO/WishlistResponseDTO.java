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

    private Long buyerId;

    private Long productId;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifiedAt;


    @Override
    public String toString() {
        return "WishlistResponseDTO{" +
                "wishlistId=" + wishlist_id +
                ", buyerId=" + buyerId +
                ", productId='" + productId + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }



}
