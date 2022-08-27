package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequestDTO {

    private String ProductName;
    private String description;
    private Integer price;
    private Integer stock;

    private List<String> category;

    public Product convertToEntity(Seller seller){
        return Product.builder()
                .seller(seller)
                .productName(this.ProductName)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .build();
    }
}
