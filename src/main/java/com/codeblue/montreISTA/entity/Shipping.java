package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.PaymentResponseDTO;
import com.codeblue.montreISTA.DTO.ShippingResponseDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "shipping")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipping extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;
    public ShippingResponseDTO convertToResponse(){
        return ShippingResponseDTO.builder()
                .shipping_id(this.getShippingId())
                .Name(this.getName())
                .Price(this.getPrice())
//                .createdAt(this.getCreatedAt())
//                .modifiedAt(this.getModifiedAt())
                .build();
    }
}
