package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.TransactionDetailDTO;
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
    @Column(columnDefinition = "TEXT")
    private String productDescription;

    public TransactionDetailDTO convertToResponse(){
        return TransactionDetailDTO.builder()
                .transaction_detail_id(this.getTransactionDetailId())
                .seller_id(this.getHistoryTransaction().getSeller().getSellerId())
                .seller_name(this.getHistoryTransaction().getSeller().getStoreName())
                .store_name(this.getHistoryTransaction().getSeller().getStoreName())
                .store_address(this.getHistoryTransaction().getSeller().getStoreAddress())
                .buyer_id(this.getHistoryTransaction().getBuyer().getBuyerId())
                .buyer_name(this.getHistoryTransaction().getBuyer().getUser().getName())
                .photo_url(this.getHistoryTransaction().getPhotoUrl())
                .total_price(this.getHistoryTransaction().getTotalPrice())
                .destination_name(this.getDestinationName())
                .destination_address(this.getDestinationAddress())
                .destination_phone(this.getDestinationPhone())
                .zip_code(this.getZipCode())
                .payment_name(this.getPaymentCode())
                .payment_code(this.getPaymentCode())
                .shipping_name(this.getShippingName())
                .shipping_price(this.getShippingPrice())
                .categories(this.getCategories())
                .product_id(this.getHistoryTransaction().getProduct_id())
                .product_name(this.getHistoryTransaction().getProduct_name())
                .product_price(this.getHistoryTransaction().getProduct_price())
                .product_description(this.getProductDescription())
                .quantity(this.getHistoryTransaction().getQuantity())
                .created_at(this.getCreatedAt())
                .modified_at(this.getModifiedAt())
                .build();
    }


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
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}
