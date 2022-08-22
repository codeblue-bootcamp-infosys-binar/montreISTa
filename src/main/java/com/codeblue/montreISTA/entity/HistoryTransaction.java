package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private Seller seller;

    @NotNull
    private String photoUrl;

    private Long product_id;
    private String product_name;

    private Integer product_price;

    private Integer quantity;

    @NotNull
    private Integer totalPrice;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "historyTransaction",
            fetch = FetchType.LAZY)
    private List<HistoryTransactionDetail> transactionDetails;

    public TransactionResponseDTO convertToResponse(){
        return TransactionResponseDTO.builder()
                .transaction_id(this.getId())
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
                "id=" + id +
                ", buyer=" + buyer +
                ", seller=" + seller +
                ", photoUrl='" + photoUrl + '\'' +
                ", product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", transactionDetails=" + transactionDetails +
                '}';
    }
}
