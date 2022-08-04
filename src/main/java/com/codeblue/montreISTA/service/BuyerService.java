//package com.codeblue.montreISTA.service;
//
//import com.codeblue.montreISTA.entity.Buyer;
//import com.codeblue.montreISTA.repository.BuyerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Optional;
//
//public class BuyerService {
//    @Autowired
//    BuyerRepository buyerRepository;
//
//    public List<Buyer> findAllBuyer() {
//        List<Buyer> buyer = buyerRepository.findAll();
//        return buyer;
//    }
//
//    public Optional<Buyer> findBuyerById(Long id) {
//        return buyerRepository.findById(id);
//    }
//
//    public Buyer createBuyer(Buyer buyer) {
//        return buyerRepository.save(buyer);
//    }
//
//    public Buyer updateBuyer(Buyer updateBuyer) {
//        return buyerRepository.save(updateBuyer);
//    }
//
//    public void deleteBuyer(Long id) {
//        buyerRepository.deleteById(id);
//    }
//
//    public List<Buyer> findBuyertByBuyerId(Long id) {
//        List<Buyer> buyer = buyerRepository.findByBuyerId(id);
//        if(buyer.isEmpty()){
//            return null;
//        } else {
//            return buyer;
//        }
//    }
//}
