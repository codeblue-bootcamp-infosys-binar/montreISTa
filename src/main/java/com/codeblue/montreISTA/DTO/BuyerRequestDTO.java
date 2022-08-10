package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerRequestDTO {

    private Long buyer_id;
    private Long user_id;

    public Buyer convertToEntity(User user){
        return Buyer.builder()
                .buyerId(this.buyer_id)
                .user(user)
                .build();
    }
}
