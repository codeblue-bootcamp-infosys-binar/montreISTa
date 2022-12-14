package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long user_id;
    private String username;
    private String email;
    private String address;
    private String name;
    private String phone;
    private String photo;
    private List<String> roles;

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email_id='" + email + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                '}';
    }
}
