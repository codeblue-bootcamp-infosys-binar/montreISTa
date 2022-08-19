package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
@Transactional
public class User extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String username;

    @Email
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String photo;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY)
    private List<UserRole> roles;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY)
    private List<Buyer> buyers;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY)
    private List<Seller> sellers;

    public UserResponseDTO convertToResponse(List<String> roles){
        return UserResponseDTO.builder()
                .user_id(this.getUserId())
                .username(this.getUsername())
                .email(this.getEmail())
                .address(this.getAddress())
                .name(this.getName())
                .photo(this.getPhoto())
                .phone(this.getPhone())
                .roles(roles)
                .build();
    }

}
