package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerResponseDTO {
    private Long buyerId;

    private Long user_id;

    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;


    @Override
    public String toString() {
        return "BuyerResponseDTO{" +
                "buyerId=" + buyerId +
                ", user_id=" + user_id +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
