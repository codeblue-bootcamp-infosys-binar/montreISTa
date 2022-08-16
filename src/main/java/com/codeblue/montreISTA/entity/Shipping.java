package com.codeblue.montreISTA.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "shipping")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipping extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

}
