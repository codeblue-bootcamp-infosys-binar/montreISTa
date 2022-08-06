package com.codeblue.montreISTA.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCartDTO {

    private Long cart_id;
    private Long buyer_id;
    private String buyer_name;
    private String buyer_username;
    private String seller_name;
    private String seller_username;
    private String store_name;
    private String store_address;
    private String product_name;
    private Integer product_price;
    private String product_description;
    private Integer quantity;


}
