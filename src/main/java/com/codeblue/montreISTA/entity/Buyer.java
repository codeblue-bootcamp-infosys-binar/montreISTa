package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "buyer",
            fetch = FetchType.LAZY)
    private List<Wishlist> wishlists;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "buyer",
            fetch = FetchType.LAZY)
    private List<HistoryTransaction> transactions;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "buyer",
            fetch = FetchType.LAZY)
    private List<Cart> carts;

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
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }

}