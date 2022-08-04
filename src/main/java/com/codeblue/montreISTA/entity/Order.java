package com.codeblue.montreISTA.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId")
public class Order extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @NotEmpty
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    @NotEmpty
    private Shipping shipping;

    @NotEmpty
    private Integer totalprice;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "order",
            fetch = FetchType.LAZY)
    private List<Cart> listCart;

}
