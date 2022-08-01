package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @NotBlank
    private Buyers buyers;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @NotBlank
    private Payments payments;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    @NotBlank
    private Shippings shippings;
    @NotBlank(message = "quantity must not be blank")
    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="orders",fetch = FetchType.LAZY)
    private List<OrdersProducts> ordersProducts;

}
