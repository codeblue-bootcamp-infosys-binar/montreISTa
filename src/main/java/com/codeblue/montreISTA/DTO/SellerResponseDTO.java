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
    private String name;
    private String username;
    private String email;
    private String store_address;
    private String store_name;
    private String store_photo;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;


}
