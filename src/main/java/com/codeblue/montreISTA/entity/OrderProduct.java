package com.codeblue.montreISTA.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders_products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderProductId")
public class OrderProduct extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Order.class)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Product.class)
    private Product product;

}
