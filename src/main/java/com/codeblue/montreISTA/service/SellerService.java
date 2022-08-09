package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {

    public List<Seller> findAllSeller();

    Optional<Seller> findSellerById(Long id);

    Seller createSeller(SellerRequestDTO seller);

    Seller updateSeller(Seller updateSeller);

    List<Seller> findSellertBySellerId(Long id);

    List<Seller> findByUsername(String keyword);

    void deleteSeller(Long id);

}
