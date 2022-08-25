package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO{

    private Long orderId;
    private List<CartResponseDTO> listCart;
    private Long payment_id;
    private String payment_name;
    private Long shipping_id;
    private String shipping_name;
    private Integer total_price;
    private String destination_name;
    private String destination_address;
    private String destination_phone;
    private String zip_code;
    private String is_payment;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "orderId=" + orderId +
                ", listCart=" + listCart +
                ", payment_id=" + payment_id +
                ", payment_name='" + payment_name + '\'' +
                ", shipping_id=" + shipping_id +
                ", shipping_name='" + shipping_name + '\'' +
                ", total_price=" + total_price +
                ", destination_name='" + destination_name + '\'' +
                ", destination_address='" + destination_address + '\'' +
                ", destination_phone='" + destination_phone + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
