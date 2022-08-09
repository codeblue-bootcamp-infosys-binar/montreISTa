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
    private String photo_url;
    private Integer total_price;
}
