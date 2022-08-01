package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders_products")
public class OrdersProducts extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Orders.class)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Products.class)
    private Products products;


}
