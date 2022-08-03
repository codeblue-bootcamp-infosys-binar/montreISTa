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

    @NotBlank
    private String storeName;

    @NotBlank
    private String storePhoto;

    @NotBlank
    private String storeAddress;

}