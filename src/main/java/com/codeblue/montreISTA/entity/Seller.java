package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "sellers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;

    @NotNull
    private String storeName;

    @NotNull
    private String storePhoto;

    @NotNull
    private String storeAddress;

    public SellerResponseDTO convertToResponse(){
        return SellerResponseDTO.builder()
                .sellerId(this.getSellerId())
                .user_id(this.getUserId().getUserId())
                .name(this.getUserId().getName())
                .username(this.getUserId().getUsername())
                .email(this.getUserId().getEmail())
                .photo(this.getUserId().getPhoto())
                .store_address(this.getStoreAddress())
                .store_name(this.getStoreName())
                .store_photo(this.getStorePhoto())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", userId=" + userId +
                ", storeName='" + storeName + '\'' +
                ", storePhoto='" + storePhoto + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                '}';
    }
}