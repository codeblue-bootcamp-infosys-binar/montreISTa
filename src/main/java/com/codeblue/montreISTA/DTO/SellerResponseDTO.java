package com.codeblue.montreISTA.DTO;


import lombok.*;

import java.time.ZonedDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SellerResponseDTO {

    private Long sellerId;

    private Long user_id;

    private String store_address;

    private String store_name;

    private String store_photo;

    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;


    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "sellerId=" + sellerId +
                ", user_id=" + user_id +
                ", store_adress='" + store_address + '\'' +
                ", store_name=" + store_name +
                ", store_photo=" + store_photo +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }

}
