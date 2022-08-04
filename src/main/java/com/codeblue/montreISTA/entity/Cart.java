package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.OrderCartDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "carts")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cartId")
public class Cart extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotEmpty
    private Product product;

    @NotEmpty
    private Integer quantity;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderCartDTO convertToOrder(){
        return OrderCartDTO.builder()
                .cart_id(this.cartId)
                .buyer_id(this.buyer.getBuyerId())
                .product_id(this.product.getProductId())
                .quantity(this.quantity)
                .build();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", buyer=" + buyer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", order=" + order +
                '}';
    }

}
