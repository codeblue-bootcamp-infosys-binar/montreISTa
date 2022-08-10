package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.BuyerRequestDTO;
import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.repository.BuyerRepository;
import java.util.List;
import java.util.Optional;

public interface BuyerService {
    public List<Buyer> findAllBuyer();

    public Optional<Buyer> findBuyerById(Long id);

    BuyerResponseDTO createBuyer(BuyerRequestDTO buyer)throws Exception ;
    Buyer updateBuyer(Buyer updateBuyer);
    void deleteBuyer(Long id);
    List<Buyer> findBuyerByBuyerId(Long id);
    List<Buyer> findByUsername(String keywoard);

}
