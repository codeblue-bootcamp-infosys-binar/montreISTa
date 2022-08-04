package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponsePost {

    private Long orderId;

    private Long paymentId;

    private Long shippingId;

    private Integer totalPrice;

    private List<Cart> listCart;

    @Override
    public String toString() {
        return "OrderResponsePost{" +
                "orderId=" + orderId +
                ", paymentId=" + paymentId +
                ", shippingId=" + shippingId +
                ", totalPrice=" + totalPrice +
                ", listCart=" + listCart +
                '}';
    }
}
