package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "buyers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Buyer extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Buyer{" +
                "buyerId=" + buyerId +
                ", user=" + user +
                '}';
    }

    public BuyerResponseDTO convertToResponse(){
        return BuyerResponseDTO.builder()
                .buyer_id(this.getBuyerId())
                .user_id(this.getUser().getUserId())
                .name(this.getUser().getName())
                .username(this.getUser().getUsername())
                .email(this.getUser().getEmail())
                .photo(this.getUser().getPhoto())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt()                )
                .build();
    }

}