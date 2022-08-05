package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.service.PhotoServiceImp;
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

    public PhotoServiceImp.Product convertToEntity(Seller seller){
        return PhotoServiceImp.Product.builder().productId(this.productId)
                .seller(seller)
                .productName(this.ProductName)
                .description(this.description)
                .price(this.price).build();
    }
}
