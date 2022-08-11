package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDTO {

    private String name;
    private String username;
    private String email_id;
    private String password;
    private String phone;
    private String photo;
    private String address;
    private List<String> roles;

    public User  convertToEntity(){
        return User.builder()
                .name(this.name)
                .username(this.username)
                .email(this.email_id)
                .password(this.password)
                .phone(this.phone)
                .photo(this.photo)
                .address(this.address)
                .build();
    }
}

