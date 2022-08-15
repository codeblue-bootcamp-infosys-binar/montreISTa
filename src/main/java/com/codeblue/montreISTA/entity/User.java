package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
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
public class User extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String phone;

    private String photo;
    private String address;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.EAGER)
    private List<UserRole> roles;

    public UserResponseDTO convertToResponse(List<String> roles){
        return UserResponseDTO.builder()
                .user_id(this.getUserId())
                .username(this.getUsername())
                .email_id(this.getEmail())
                .address(this.getAddress())
                .name(this.getName())
                .phone(this.getPhone())
                .roles(roles)
                .build();
    }
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", address='" + address + '\'' +
                ", roles=" + roles +
                '}';
    }
}
