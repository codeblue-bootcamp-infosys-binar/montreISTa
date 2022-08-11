package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    List<SellerResponseDTO> findAllSeller();
    SellerResponseDTO findSellerById(Long id) throws Exception;
    SellerResponseDTO createSeller(SellerRequestDTO seller) throws Exception;
    SellerResponseDTO updateSeller(SellerRequestDTO seller,Long id)throws Exception;
    void deleteSeller(Long id);
    List<SellerResponseDTO> findByUsername(String keyword);


}
