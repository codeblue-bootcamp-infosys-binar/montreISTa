package com.codeblue.montreISTA.DTO;


import com.codeblue.montreISTA.entity.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerRequestDTO {

    private Long userId;
    private String storeName;
    private String storePhoto;
    private String storeAddress;



public Seller convertToEntity(User user){
    return Seller.builder()

            .userId(user)
            .storeName(this.storeName)
            .storePhoto(this.storePhoto)
            .storeAddress(this.storeAddress)
            .build();
     }


}
