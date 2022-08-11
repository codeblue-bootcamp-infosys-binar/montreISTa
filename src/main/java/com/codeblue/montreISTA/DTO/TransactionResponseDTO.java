package com.codeblue.montreISTA.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long transaction_id;
    private Long buyer_id;
    private Long seller_id;
    private String store_name;
    private String photo_url;
    private String product_name;
    private Integer product_price;
    private Integer quantity;
    private Integer total_price;
}
