package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SellerService {
    List<SellerResponseDTO> findAllSeller();
    SellerResponseDTO findSellerById(Long id) throws Exception;
    Object createSeller(SellerRequestDTO seller, Authentication authentication) throws Exception;
    SellerResponseDTO updateSeller(SellerRequestDTO seller,Authentication authentication)throws Exception;
    void deleteSeller(Long id);
    List<SellerResponseDTO> findByUsername(String keyword);


}
