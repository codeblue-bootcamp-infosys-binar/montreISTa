package com.codeblue.montreISTA.DTO;


import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingResponseDTO {

    private Long shipping_id;

    private String Name;

    private Integer Price;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifiedAt;


    @Override
    public String toString() {
        return "ShippingResponseDTO{" +
                "shipping_id=" + shipping_id +
                ", Name=" + Name +
                ", Price='" + Price + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }


}
