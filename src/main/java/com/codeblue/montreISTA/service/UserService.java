package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.RegistrationDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public List<UserResponseDTO> findAllUser();
    public UserResponseDTO findByUserId(Long id) throws Exception;
    UserResponseDTO findByUsername(String keyword)throws Exception;
    public UserResponseDTO registrationUser(RegistrationDTO user)throws Exception;
    public UserResponseDTO updateUser(RegistrationDTO user, Authentication authentication)throws Exception;
    public UserResponseDTO uploadPhotoProfile(Authentication authentication, MultipartFile file)throws Exception;
    public void deleteUser(Long id)throws Exception;
}