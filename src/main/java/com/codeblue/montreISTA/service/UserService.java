package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.LoginUserRequest;
import com.codeblue.montreISTA.DTO.RegistrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseEntity<Object> authenticationUser(LoginUserRequest userRequest)throws Exception;
    ResponseEntity<Object> findAllUser() throws Exception;
    ResponseEntity<Object> findByUserId(Long id) throws Exception;
    ResponseEntity<Object> findMyProfile(String keyword)throws Exception;
    ResponseEntity<Object> registrationUser(RegistrationDTO user)throws Exception;
    ResponseEntity<Object> updateUser(RegistrationDTO user, Authentication authentication)throws Exception;
    ResponseEntity<Object> uploadPhotoProfile(Authentication authentication, MultipartFile file)throws Exception;
    ResponseEntity<Object> deleteUser(Long id)throws Exception;
}