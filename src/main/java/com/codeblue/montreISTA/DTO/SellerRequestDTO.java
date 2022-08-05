package com.codeblue.montreISTA.DTO;


import com.codeblue.montreISTA.entity.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerRequestDTO {

    private Long sellerId;

    private User userId;

    private String storeName;

    private String storePhoto;

    private String storeAddress;



public Seller convertToEntity(){
    return Seller.builder()
            .sellerId(this.sellerId)
            .userId(this.userId)
            .storeName(this.storeName)
            .storePhoto(this.storePhoto)
            .storeAddress(this.storeAddress)
            .build();
     }


}
