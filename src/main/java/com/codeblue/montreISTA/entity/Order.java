package com.codeblue.montreISTA.entity;


import com.codeblue.montreISTA.DTO.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
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
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;
    private Integer totalprice;

    private String destinationName;
    @Column(columnDefinition = "TEXT")
    private String destinationAddress;
    private String destinationPhone;
    private String zipCode;

    private Boolean isPay;

    //list cart
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "order",
            fetch = FetchType.LAZY)
    private List<Cart> listCart;

    public OrderResponseCartDTO convertCart(List<OrderCartDTO> carts){
       String print = this.checkIsPay(this.getIsPay());
        return OrderResponseCartDTO.builder()
                .orderId(this.getOrderId())
                .total_price(this.getTotalprice())
                .is_payment(print)
                .cart(carts)
                .build();
    }

    public OrderResponseDTO convertToResponse(List<CartResponseDTO> cartDTO){
        String print = this.checkIsPay(this.getIsPay());
        return OrderResponseDTO.builder()
                .orderId(this.getOrderId())
                .listCart(cartDTO)
                .payment_id(this.getPayment().getPaymentId())
                .payment_name(this.getPayment().getName())
                .shipping_id(this.getShipping().getShippingId())
                .shipping_name(this.getShipping().getName())
                .total_price(this.getTotalprice())
                .destination_name(this.getDestinationName())
                .destination_address(this.getDestinationAddress())
                .destination_phone(this.getDestinationPhone())
                .zip_code(this.getZipCode())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .is_payment(print)
                .build();
        }
    public String checkIsPay(Boolean isPay) {
        String print = null;
        if (this.getIsPay()) {
            print = "Paid";
        } else {
            print = "Unpaid";
        }
        return print;
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
