package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponsePost {

    private Long order_id;
    private Long payment_id;
    private Long shipping_id;
    private Integer totalPrice;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    @Override
    public String toString() {
        return "OrderResponsePost{" +
                "order_id=" + order_id +
                ", payment_id=" + payment_id +
                ", shipping_id=" + shipping_id +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
