package com.codeblue.montreISTA.DTO;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductToProductCategoryDTO {
         private Long productId;
        private Long sellerId;
        private String storeName;
        private String storePhoto;
        private String productName;
       private String description;
       private Integer price;

}
