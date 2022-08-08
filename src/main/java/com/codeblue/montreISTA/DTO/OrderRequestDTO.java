package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long paymentId;

    private Long shippingId;


    public Order convertToEntity(Payment payment, Shipping shipping){
        return Order.builder()
                .payment(payment)
                .shipping(shipping)
                .build();
    }
}
