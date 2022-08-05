package com.codeblue.montreISTA.DTO;


import com.codeblue.montreISTA.entity.AuditEntity;
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
    private String shipping_name;
    private Integer total_price;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "orderId=" + orderId +
                ", listCart=" + listCart +
                ", shipping_name='" + shipping_name + '\'' +
                ", total_price=" + total_price +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
