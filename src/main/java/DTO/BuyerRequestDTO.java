package DTO;

import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerRequestDTO {
    private Long buyerId;
    private User userId;

    public Buyer convertToEntity(){
        return Buyer.builder()
                .buyerId(this.buyerId)
                .user(this.userId)
                .build();
    }
}
