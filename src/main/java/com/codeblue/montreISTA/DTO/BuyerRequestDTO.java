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


   private Long user_id;


    public Buyer convertToEntity(User user){
        return Buyer.builder()
                .user(user)
                .build();
    }

}
