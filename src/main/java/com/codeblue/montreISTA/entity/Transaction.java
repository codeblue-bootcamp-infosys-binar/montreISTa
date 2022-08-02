package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name="transactions")
public class Transaction extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "orderProduct_id")
    @NotBlank(message= "orders must not be blank")
    private OrderProduct orderProduct;

    @NotBlank(message= "totalPrice must not be blank")
    private Integer totalPrice;

    @NotBlank(message= "dateTransaction must not be blank")
    private ZonedDateTime dateTransaction;

}
