package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.BuyerRequestDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BuyerService {

    private BuyerRepository buyerRepository;

    public List<Buyer> findAllBuyer() {
        List<Buyer> buyers = buyerRepository.findAll();
        return buyers;
    }

    public Optional<Buyer> findBuyerById(Long id) {
        return buyerRepository.findById(id);
    }

    public Buyer createBuyer(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public Buyer updateBuyer(Buyer updateBuyer) {
        return buyerRepository.save(updateBuyer);
    }

    public void deleteBuyer(Long id) {
        buyerRepository.deleteById(id);
    }

    public List<Buyer> findBuyerByBuyerId(Long id) {
        List<Buyer> buyer = buyerRepository.findByBuyerId(id);
        if(buyer.isEmpty()){
            return null;
        } else {
            return buyer;
        }
    }
    /*List<Buyer> findAllBuyer();
    Optional<Buyer> findBuyerById(Long id);
    Buyer createBuyer(BuyerRequestDTO buyer)throws Exception ;
    Buyer updateBuyer(Buyer updateBuyer);
    void deleteBuyer(Long id);
    List<Buyer> findBuyerByBuyerId(Long id);
    List<Buyer> findByUsername(String keywoard);
    */
}
