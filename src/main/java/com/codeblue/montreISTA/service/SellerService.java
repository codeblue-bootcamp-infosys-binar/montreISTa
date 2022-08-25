package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.LoginSellerResponseDTO;
import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SellerService {
    ResponseEntity<Object> findAllSeller();
    ResponseEntity<Object> findSellerById(Long id) throws Exception;
    ResponseEntity<Object>createSeller(SellerRequestDTO seller, Authentication authentication) throws Exception;
    ResponseEntity<Object> updateSeller(SellerRequestDTO seller,Authentication authentication)throws Exception;
    ResponseEntity<Object> uploadPhotoStore(Authentication authentication, MultipartFile file)throws Exception;

    ResponseEntity<Object> loginAsSeller(String keyword,Integer page, String sort, boolean descending) throws Exception;
    ResponseEntity<Object> deleteSeller(Long id, Authentication authentication)throws Exception;
    ResponseEntity<Object> findByUsername(String keyword)throws Exception;


}
