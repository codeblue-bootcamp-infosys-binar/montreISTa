package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductCategoryResponseDTO {
    private Long product_category_id;
    private ProductToProductCategoryDTO product;
    private CategoryResponseDTO category;

}
