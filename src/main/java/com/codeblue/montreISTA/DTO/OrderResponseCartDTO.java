package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseCartDTO {
    private Long orderId;
    private Integer total_price;
    private List<OrderCartDTO> cart;
    private String is_payment;

    @Override
    public String toString() {
        return "OrderResponseCartDTO{" +
                "orderId=" + orderId +
                ", total_price=" + total_price +
                ", cart=" + cart +
                ", is_payment=" + is_payment +
                '}';
    }
}
