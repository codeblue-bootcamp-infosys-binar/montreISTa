package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import org.springframework.security.core.Authentication;
import java.util.List;


public interface BuyerService {
    List<BuyerResponseDTO> findAllBuyer();

    BuyerResponseDTO findBuyerById(Long id) throws Exception;

    List<ProductResponseDTO> createBuyer(Authentication authentication,Integer page, String sort, boolean descending)throws Exception ;

    void deleteBuyer(Long id, Authentication authentication)throws Exception;

    List<BuyerResponseDTO> findByUsername(String keywoard)throws Exception;

}
