package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long orderId;

    private Payment payment;

    private Shipping shipping;

    private Integer totalPrice;


    public Order covertToEntitiy(){
        return Order.builder()
                .orderId(this.orderId)
                .payment(this.payment)
                .shipping(this.shipping)
                .totalPrice(this.totalPrice)
                .build();
    }
}
