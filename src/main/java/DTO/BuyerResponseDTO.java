package DTO;

import com.codeblue.montreISTA.entity.User;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerResponseDTO {

    private Long buyerId;
    private User userId;

    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    @Override
    public String toString() {
       return "OrderResponseDTO{" +
                "buyerId=" + buyerId +
                ", userId=" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }


}
