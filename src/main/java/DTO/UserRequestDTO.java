package DTO;

import com.codeblue.montreISTA.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    private Long user_id;

    private String username;

    private String email_id;

    private String password;

    public User  convertToEntity(){
        return User.builder().userId(this.user_id).username(this.username).email(this.email_id).password(this.password).build();
    }
}

