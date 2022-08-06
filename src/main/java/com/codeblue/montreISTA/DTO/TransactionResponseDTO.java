package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionResponseDTO {
    private Long transaction_id;
    private String name_shipping;
    private String address_shipping;
    private String zip_code;
    private String phone_shipping;
    private Integer total_price;
    private String payment_name;
    private String payment_code;
    private String shipping_name;
    private Integer shipping_price;
    private List<CartResponseDTO> carts;
    private ZonedDateTime created_at;
    private ZonedDateTime modified_at;
}
