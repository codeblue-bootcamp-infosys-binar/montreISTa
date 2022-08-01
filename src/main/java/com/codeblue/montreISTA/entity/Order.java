package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @NotBlank
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @NotBlank
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    @NotBlank
    private Shipping shipping;

    @NotBlank(message = "quantity must not be blank")
    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "order",
            fetch = FetchType.LAZY)
    private List<OrderProduct> OrderProduct;
}
