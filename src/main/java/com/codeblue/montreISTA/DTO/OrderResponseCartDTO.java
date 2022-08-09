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

    @Override
    public String toString() {
        return "OrderResponseCartDTO{" +
                "orderId=" + orderId +
                ", total_price=" + total_price +
                ", cart=" + cart +
                '}';
    }
}
