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
    private String email_id;
    private String address;
    private String name;
    private String phone;
    private String  photo;
    private List<String> roles;

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email_id='" + email_id + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
