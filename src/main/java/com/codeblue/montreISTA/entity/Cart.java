package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.DTO.OrderCartDTO;
import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.service.PhotoServiceImp;
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
    
    public OrderCartDTO convertToOrder(){
        return OrderCartDTO.builder()
                .cart_id(this.cartId)
                .buyer_name(this.getBuyer().getUser().getName())
                .product_name(this.getProduct().getProductName())
                .description(this.getProduct().getDescription())
                .store_name(this.getProduct().getSeller().getStoreName())
                .store_address(this.getProduct().getSeller().getStoreAddress())
                .quantity(this.quantity)
                .build();
    }
    public CartResponseDTO convertToResponse(List<PhotoProductDTO> photoDTO, List<String> categories){
        return CartResponseDTO.builder()
                .card_id(this.getCartId())
                .buyer_name(this.getBuyer().getUser().getName())
                .buyer_username(this.getBuyer().getUser().getUsername())
                .seller_name(this.getProduct().getSeller().getUserId().getName())
                .seller_username(this.getProduct().getSeller().getUserId().getUsername())
                .product_name(this.getProduct().getProductName())
                .product_price(this.getProduct().getPrice())
                .product_description(this.getProduct().getDescription())
                .quantity(this.getQuantity())
                .photos(photoDTO)
                .categories(categories)
                .created_at(this.getCreatedAt())
                .modified_at(this.getModifiedAt())
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
