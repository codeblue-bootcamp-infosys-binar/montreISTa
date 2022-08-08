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
    private String payment_name;
    private Long shipping_id;
    private String shipping_name;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    @Override
    public String toString() {
        return "OrderResponsePost{" +
                "order_id=" + order_id +
                ", payment_id=" + payment_id +
                ", payment_name=" + payment_name + '\'' +
                ", shipping_id=" + shipping_id +
                ", shipping_name='" + shipping_name + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
