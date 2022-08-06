package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartRequestDTO {
    private Long buyer_id;
    private Long product_id;
    private Integer quantity;

    public Cart convertToEntity(Buyer buyerDTO, Product productDTO, Order orderDTO){
        return Cart.builder()
                .buyer(buyerDTO)
                .product(productDTO)
                .order(orderDTO)
                .quantity(this.quantity)
                .build();
    }
}
