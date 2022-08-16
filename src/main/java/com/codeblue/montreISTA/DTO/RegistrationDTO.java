package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDTO {
    @Size(min = 3, max = 40)
    private String name;
    @Size(max = 50)
    private String username;
    @Size(max = 50)
    private String email;
    @Size(min = 6, max=40)
    private String password;
    @Size(min = 11, max=14)
    private String phone;
    private String photo;
    @Size(min=5)
    private String address;
    private List<String> roles;

    public User convertToEntity(){
        return User.builder()
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .phone(this.phone)
                .photo(this.photo)
                .address(this.address)
                .build();
    }
}

