package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.BuyerRequestDTO;
import com.codeblue.montreISTA.entity.Buyer;

import java.util.List;
import java.util.Optional;

public interface BuyerService {
    List<Buyer> findAllBuyer();
    Optional<Buyer> findBuyerById(Long id);
    Buyer createBuyer(BuyerRequestDTO buyer)throws Exception ;
    Buyer updateBuyer(Buyer updateBuyer);
    void deleteBuyer(Long id);
    List<Buyer> findBuyerByBuyerId(Long id);
    List<Buyer> findByUsername(String keywoard);
}
