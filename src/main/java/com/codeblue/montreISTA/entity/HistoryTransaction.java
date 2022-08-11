package com.codeblue.montreISTA.entity;


import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="history_transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryTransaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyTransactionId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private Seller seller;

    @NotNull
    private String photoName;

    @NotNull
    private String photoUrl;

    private Long product_id;
    private String product_name;

    private Integer product_price;

    private Integer quantity;

    @NotNull
    private Integer totalPrice;

    public TransactionResponseDTO convertToResponse(){
        return TransactionResponseDTO.builder()
                .transaction_id(this.getHistoryTransactionId())
                .buyer_id(this.getBuyer().getBuyerId())
                .store_name(this.getSeller().getStoreName())
                .seller_id(this.getSeller().getSellerId())
                .photo_url(this.getPhotoUrl())
                .product_name(this.getProduct_name())
                .product_price(this.getProduct_price())
                .quantity(this.getQuantity())
                .total_price(this.getTotalPrice())
                .build();
    }

    @Override
    public String toString() {
        return "HistoryTransaction{" +
                "historyTransactionId=" + historyTransactionId +
                ", buyer=" + buyer +
                ", seller=" + seller +
                ", photoName='" + photoName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
