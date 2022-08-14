package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.BuyerRequestDTO;
import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.repository.BuyerRepository;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface BuyerService {
    List<BuyerResponseDTO> findAllBuyer();

    BuyerResponseDTO findBuyerById(Long id) throws Exception;

    BuyerResponseDTO createBuyer(Authentication authentication)throws Exception ;
    BuyerResponseDTO updateBuyer(BuyerRequestDTO buyer, Long id)throws Exception;
    void deleteBuyer(Long id);

    List<BuyerResponseDTO> findByUsername(String keywoard);

}
