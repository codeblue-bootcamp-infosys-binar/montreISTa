package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.PaymentResponseDTO;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private String paymentCode;

    public PaymentResponseDTO convertToResponse(){
        return PaymentResponseDTO.builder()
                .Payment_id(this.paymentId)
                .Name(this.name)
                .Payment_Code(this.paymentCode)
//                .createdAt(this.getCreatedAt())
//                .modifiedAt(this.getModifiedAt())
                .build();
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", name='" + name + '\'' +
                ", paymentCode='" + paymentCode + '\'' +
                '}';
    }
}
