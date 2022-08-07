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
    private List<OrderCartDTO> listCart;
    private Long payment_id;
    private String payment_name;
    private Long shipping_id;
    private String shipping_name;
    private Integer total_price;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "orderId=" + orderId +
                ", listCart=" + listCart +
                ", payment_id=" + payment_id +
                ", payment_name=" + payment_name + '\'' +
                ", shipping_id=" + shipping_id +
                ", shipping_name='" + shipping_name + '\'' +
                ", total_price=" + total_price +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
