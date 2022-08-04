package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequestDTO {

    private Long productId;
    private Long sellerId;
    private String ProductName;
    private String description;
    private Integer price;

    public Product convertToEntity(Seller seller){
        return Product.builder().productId(this.productId)
                .seller(seller)
                .productName(this.ProductName)
                .description(this.description)
                .price(this.price).build();
    }
}
