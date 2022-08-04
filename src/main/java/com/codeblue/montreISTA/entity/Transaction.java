package com.codeblue.montreISTA.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name="transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "transactionId")
public class Transaction extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotBlank(message= "orders must not be blank")
    private Order order;

    @ManyToOne
    @NotNull
    @JoinColumn(name="seller_id")
    private Seller seller;

    @ManyToOne
    @NotNull
    @JoinColumn(name="buyer_id")
    private Buyer buyer;

    private String nameShipping;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    private String addressShipping;

    @NotNull
    private String zipCode;

    @NotNull
    private String phoneShipping;

}
