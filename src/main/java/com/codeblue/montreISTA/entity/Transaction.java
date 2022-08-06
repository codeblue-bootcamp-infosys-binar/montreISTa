package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.DTO.OrderCartDTO;
import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "transactionId")
public class Transaction extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name="seller_id")
//    private Seller seller;
//
//    @ManyToOne
//    @JoinColumn(name="buyer_id")
//    private Buyer buyer;

    @NotEmpty
    private String nameShipping;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    private String addressShipping;

    @NotEmpty
    private String zipCode;

    @NotEmpty
    private String phoneShipping;

    public TransactionResponseDTO convertToResponse(List<CartResponseDTO> carts){
        return TransactionResponseDTO.builder()
                .transaction_id(this.getTransactionId())
                .name_shipping(this.getNameShipping())
                .address_shipping(this.getAddressShipping())
                .zip_code(this.getZipCode())
                .phone_shipping(this.getPhoneShipping())
                .total_price(this.getOrder().getTotalprice())
                .payment_name(this.getOrder().getPayment().getName())
                .payment_code(this.getOrder().getPayment().getPaymentCode())
                .shipping_name(this.getOrder().getShipping().getName())
                .shipping_price(this.getOrder().getShipping().getPrice())
                .carts(carts)
                .created_at(this.getCreatedAt())
                .modified_at(this.getModifiedAt())
                .build();
    }
}
