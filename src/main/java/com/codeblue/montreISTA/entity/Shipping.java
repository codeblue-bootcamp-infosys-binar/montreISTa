package com.codeblue.montreISTA.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

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

    @NotBlank(message = "name must not be blank")
    @Column(unique = true)
    private String name;

    @NotBlank(message= "price must not be blank")
    private Integer price;

}
