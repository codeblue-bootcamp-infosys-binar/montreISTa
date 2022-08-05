package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.ProductCategory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductCategoryRequestDTO {
    private Long product_id;
    private Long category_id;
    public ProductCategory convertToEntity(Product product, Category category)
    {
        return ProductCategory.builder()
                .product(product)
                .category(category)
                .build();
    }
}
