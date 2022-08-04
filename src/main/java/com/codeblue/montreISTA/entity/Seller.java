package com.codeblue.montreISTA.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

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

    @NotBlank(message = "store name may not be blank")
    private String storeName;

    @NotBlank(message = "store photo may not be blank")
    private String storePhoto;

    @NotBlank(message = "store addres may not be blank")
    private String storeAddress;

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