package com.codeblue.montreISTA.DTO;

import lombok.*;

import javax.persistence.Column;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailDTO {
    private Long transaction_detail_id;
    private Long seller_id;
    private String seller_name;
    private String store_name;
    private String store_address;
    private Long buyer_id;
    private String buyer_name;
    private String photo_name;
    private String photo_url;

    private String destination_name;
    private String destination_address;
    private String destination_phone;
    private String zip_code;
    private String payment_name;
    private String payment_code;
    private String shipping_name;
    private String categories;
    private Long product_id;
    private String product_name;
    private String product_description;
    private Integer product_price;
    private Integer quantity;
    private Integer shipping_price;
    private Integer total_price;
    private ZonedDateTime created_at;
    private ZonedDateTime modified_at;
}
