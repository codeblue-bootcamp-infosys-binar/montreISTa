package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    private String payment;
    private String shipping;
    private String destination_name;
    private String destination_address;
    private String destination_phone;
    private String zip_code;


    public Order convertToEntity(Payment payment, Shipping shipping){
        return Order.builder()
                .payment(payment)
                .shipping(shipping)
                .destinationName(this.getDestination_name())
                .destinationAddress(this.getDestination_address())
                .destinationName(this.getDestination_phone())
                .destinationPhone(this.getDestination_phone())
                .zipCode(this.getZip_code())
                .build();
    }
}
