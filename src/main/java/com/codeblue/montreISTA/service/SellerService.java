package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    List<Seller> findAllSeller();
    Optional<Seller> findSellerById(Long id);
    Seller createSeller(SellerRequestDTO seller) throws Exception;
    Seller updateSeller(Seller updateSeller);
    void deleteSeller(Long id);
    List<Seller> findSellertBySellerId(Long id);
    List<Seller> findByUsername(String keyword);
    }
