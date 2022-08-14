package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.entity.Seller;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    List<SellerResponseDTO> findAllSeller();
    SellerResponseDTO findSellerById(Long id) throws Exception;
    SellerResponseDTO createSeller(SellerRequestDTO seller, Authentication authentication) throws Exception;
    SellerResponseDTO updateSeller(SellerRequestDTO seller,Authentication authentication)throws Exception;
    void deleteSeller(Long id);
    List<SellerResponseDTO> findByUsername(String keyword);


}
