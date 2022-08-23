package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.LoginSellerResponseDTO;
import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SellerService {
    List<SellerResponseDTO> findAllSeller();
    SellerResponseDTO findSellerById(Long id) throws Exception;
    SellerResponseDTO createSeller(SellerRequestDTO seller, Authentication authentication) throws Exception;
    SellerResponseDTO updateSeller(SellerRequestDTO seller,Authentication authentication)throws Exception;
    SellerResponseDTO uploadPhotoStore(Authentication authentication, MultipartFile file)throws Exception;

    LoginSellerResponseDTO loginAsSeller(String keyword,Integer page, String sort, boolean descending) throws Exception;
    void deleteSeller(Long id, Authentication authentication)throws Exception;
    SellerResponseDTO findByUsername(String keyword)throws Exception;


}
