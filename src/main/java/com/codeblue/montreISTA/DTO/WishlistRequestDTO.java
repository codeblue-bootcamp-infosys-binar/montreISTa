package com.codeblue.montreISTA.DTO;


import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Wishlist;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistRequestDTO {


    private Long buyerId;

    private Long productId;

    private Integer quantity;

    public Wishlist convertToEntity(Buyer buyer, Product product){
        return Wishlist.builder()
                .buyer(buyer)
                .product(product)
                .quantity(this.quantity)
                .build();
    }

}