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

    private Long wishlistId;

    private Buyer buyer;

    private Product product;

    public Wishlist convertToEntity(Wishlist wishlist){
        return Wishlist.builder()
                .wishlistId(this.wishlistId)
                .buyer(this.buyer)
                .product(this.product)
                .build();
    }

}
