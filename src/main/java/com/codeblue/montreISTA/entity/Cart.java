package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.DTO.OrderCartDTO;
import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
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
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public OrderCartDTO convertToOrder(String photo){
        return OrderCartDTO.builder()
                .cart_id(this.cartId)
                .buyer_id(this.getBuyer().getBuyerId())
                .buyer_name(this.getBuyer().getUser().getName())
                .product_id(this.getProduct().getId())
                .store_name(this.getProduct().getSeller().getStoreName())
                .product_name(this.getProduct().getProductName())
                .product_price(this.getProduct().getPrice())
                .quantity(this.quantity)
                .photo_product(photo)
                .build();
    }
    public CartResponseDTO convertToResponse(List<PhotoProductDTO> photoDTO, List<String> categories){
        return CartResponseDTO.builder()
                .cart_id(this.getCartId())
                .buyer_name(this.getBuyer().getUser().getName())
                .buyer_username(this.getBuyer().getUser().getUsername())
                .seller_name(this.getProduct().getSeller().getUser().getName())
                .seller_username(this.getProduct().getSeller().getUser().getUsername())
                .product_name(this.getProduct().getProductName())
                .product_price(this.getProduct().getPrice())
                .store_name(this.getProduct().getSeller().getStoreName())
                .store_address(this.getProduct().getSeller().getStoreName())
                .product_description(this.getProduct().getDescription())
                .quantity(this.getQuantity())
                .photos(photoDTO)
                .categories(categories)
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
