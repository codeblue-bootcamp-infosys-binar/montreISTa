package com.codeblue.montreISTA.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "name may not be blank")
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    @NotBlank(message = "username may not be blank")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "email may not be blank")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotBlank(message = "password may not be blank")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "phone number may not be blank")
    private String phone;

    @NotBlank(message = "photo url may not be blank")
    private String photo;

    @NotBlank(message = "address may not be blank")
    private String address;

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
                '}';
    }
}
