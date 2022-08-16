package com.codeblue.montreISTA.DTO;


import com.codeblue.montreISTA.entity.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerRequestDTO {

    private String storeName;
    private String storeAddress;

public Seller convertToEntity(User user){
    return Seller.builder()
            .userId(user)
            .storeName(this.storeName)
            .storeAddress(this.storeAddress)
            .build();
     }


}
