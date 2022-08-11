package com.codeblue.montreISTA.DTO;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginUserRequest {
    private String username;
    private String password;
}
