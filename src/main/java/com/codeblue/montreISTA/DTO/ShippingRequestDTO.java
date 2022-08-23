package com.codeblue.montreISTA.DTO;


import com.codeblue.montreISTA.entity.Shipping;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingRequestDTO {

    private String name;
    private Integer price;


    public Shipping convertToEntity(){
        return Shipping.builder()
                .name(this.name)
                .price(this.price)
                .build();
    }


}
