package com.codeblue.montreISTA.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCartDTO {

    private Long cart_id;
    private String buyer_name;
    private String product_name;
    private Integer quantity;
}
