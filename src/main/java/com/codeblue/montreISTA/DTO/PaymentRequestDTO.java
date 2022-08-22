package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Payment;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {

    private String name;
    private String payment_code;

    public Payment convertToEntity(){
        return Payment.builder()
                .name(this.name)
                .paymentCode(this.payment_code)
                .build();
    }
}
