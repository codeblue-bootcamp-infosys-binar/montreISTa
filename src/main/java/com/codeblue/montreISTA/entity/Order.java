package com.codeblue.montreISTA.entity;


import com.codeblue.montreISTA.DTO.OrderCartDTO;
import com.codeblue.montreISTA.DTO.OrderResponseDTO;
import com.codeblue.montreISTA.DTO.OrderResponsePost;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId")
public class Order extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @NotNull
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    @NotNull
    private Shipping shipping;

    @NotNull
    private Integer totalprice;


    //list cart
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "order",
            fetch = FetchType.LAZY)
    private List<Cart> listCart;

    public OrderResponseDTO convertToResponse(List<OrderCartDTO> cartDTO){
        return OrderResponseDTO.builder()
                .orderId(this.orderId)
                .listCart(cartDTO)
                .payment_name(this.payment.getName())
                .shipping_name(this.shipping.getName())
                .total_price(this.totalprice)
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
        }

        public OrderResponsePost convertToResponsePost(){
        return OrderResponsePost.builder()
                .order_id(this.getOrderId())
                .payment_id(this.getPayment().getPaymentId())
                .shipping_id(this.getShipping().getShippingId())
                .totalPrice(this.getTotalprice())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", payment=" + payment +
                ", shipping=" + shipping +
                ", totalPrice=" + totalprice +
                ", listCart=" + listCart +
                '}';
    }


}
