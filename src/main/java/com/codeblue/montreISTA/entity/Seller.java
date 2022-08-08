package com.codeblue.montreISTA.entity;

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