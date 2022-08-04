package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.entity.Seller;
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

    private List<Photo> photos;

    private ZonedDateTime createdAt;

    private ZonedDateTime modifiedAt;
}
