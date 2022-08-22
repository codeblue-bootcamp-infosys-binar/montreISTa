package com.codeblue.montreISTA.DTO;

import lombok.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long Payment_id;
    private String Name;
    private String Payment_Code;
//    private ZonedDateTime createdAt;
//    private ZonedDateTime modifiedAt;

    @Override
    public String toString() {
        return "PaymentResponseDTO{" +
                "Payment_id=" + Payment_id +
                ", Name='" + Name + '\'' +
                ", Payment_Code=" + Payment_Code +
//                ", createdAt=" + createdAt +
//                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
