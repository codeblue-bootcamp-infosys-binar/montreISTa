package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.User;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerResponseDTO {

    private Long buyer_id;
    private Long user_id;
    private String name;
    private String username;
    private String email;
    private String photo;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;


    @Override
    public String toString() {
        return "BuyerResponseDTO{" +
                "buyer_id=" + buyer_id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
