//package com.codeblue.montreISTA.DTO;
//
//import com.codeblue.montreISTA.entity.User;
//import lombok.*;
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class UserRequestDTO {
//    private Long user_id;
//    private String name;
//    private String username;
//    private String email;
//    private String phone;
//    private String photo;
//    private String addres;
//
//
//    private String password;
//
//    public User convertToEntity() {
//        return User.builder()
//                .userId(this.user_id)
//                .name(this.name)
//                .username(this.username)
//                .email(this.email)
//                .phone(this.phone)
//                .photo(this.photo)
//                .address(this.addres)
//                .password(this.password)
//                .build();
//    }
//}