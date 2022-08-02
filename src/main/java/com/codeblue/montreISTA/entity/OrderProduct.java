package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders_products")
public class OrderProduct extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Order.class)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = ProductCategory.class)
    private ProductCategory productCategory;


}
