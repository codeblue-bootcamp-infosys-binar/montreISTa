package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseDTO {

    private Long productId;

    private Long sellerId;

    private String storeName;

    private String storePhoto;

    private String productName;

    private String description;

    private Integer price;

    private List<PhotoProductDTO> photos;

    private List<String> categories;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifiedAt;
}
