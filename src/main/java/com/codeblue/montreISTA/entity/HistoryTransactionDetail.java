package com.codeblue.montreISTA.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name="history_transactions_details")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryTransactionDetail extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionDetailId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_transaction_id")
    private HistoryTransaction historyTransaction;

    @NotEmpty
    private String destinationName;
    @Column(columnDefinition = "TEXT")
    private String destinationAddress;
    private String destinationPhone;
    private String zipCode;
    private String paymentName;
    private String paymentCode;
    private String shippingName;
    private Integer shippingPrice;
    private String categories;
    private Long productId;
    private String productName;
    private Integer productPrice;
    @Column(columnDefinition = "TEXT")
    private String productDescription;
    private Integer quantity;

    @Override
    public String toString() {
        return "HistoryTransactionDetail{" +
                "transactionDetailId=" + transactionDetailId +
                ", historyTransaction=" + historyTransaction +
                ", destinationName='" + destinationName + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", destinationPhone='" + destinationPhone + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", paymentName='" + paymentName + '\'' +
                ", paymentCode='" + paymentCode + '\'' +
                ", shippingName='" + shippingName + '\'' +
                ", shippingPrice=" + shippingPrice +
                ", categories='" + categories + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
