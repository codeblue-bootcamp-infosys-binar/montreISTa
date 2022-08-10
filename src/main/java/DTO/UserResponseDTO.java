package DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long user_id;

    private String username;

    private String email_id;

    private String addres;

    private String name;

    private String phone;

    private String  photo;

    @Override
    public String toString() {
        return "UserResponDTO{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email_id='" + email_id + '\'' +
                ", addres='" + addres + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
