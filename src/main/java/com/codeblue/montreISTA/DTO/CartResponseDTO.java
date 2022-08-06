package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartResponseDTO {
    private Long cart_id;
    private String buyer_name;
    private String buyer_username;
    private String seller_name;
    private String seller_username;
    private String product_name;
    private Integer product_price;
    private String product_description;
    private Integer quantity;
    private List<PhotoProductDTO> photos;
    private List<String> categories;
    private ZonedDateTime created_at;
    private ZonedDateTime modified_at;
}
