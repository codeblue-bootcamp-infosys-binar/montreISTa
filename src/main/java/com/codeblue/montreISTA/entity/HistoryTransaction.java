package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "history_transaction")
public class HistoryTransaction extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @NotBlank
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @NotBlank
    private Seller seller;

    @NotBlank
    private String paymentName;

    @NotBlank
    private String paymentCode;

    @NotBlank
    private String shippingName;

    @NotBlank
    private Integer totalPrice;

    @NotBlank
    private ZonedDateTime transactionDate;
}
