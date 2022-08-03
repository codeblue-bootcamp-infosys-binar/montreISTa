package com.codeblue.montreISTA.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders_products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Order.class)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Product.class)
    private Product product;

}
