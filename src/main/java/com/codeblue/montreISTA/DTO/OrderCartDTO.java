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
    private Long product_id;
    private String product_name;
    private Integer product_price;
    private Integer quantity;
    private String photo_product;

}
