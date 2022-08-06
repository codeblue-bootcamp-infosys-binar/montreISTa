package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;

    public List<Seller> findAllSeller() {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers;
    }

    public Optional<Seller> findSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller updateSeller(Seller updateSeller) {
        return sellerRepository.save(updateSeller);
    }

    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    public List<Seller> findSellertBySellerId(Long id) {
        List<Seller> seller = sellerRepository.findBySellerId(id);
        if(seller.isEmpty()){
            return null;
        } else {
            return seller;
        }
    }

    public List<Seller> findByUsername(String keyword) {
        List<Seller> sellerUsername = sellerRepository.findByUserIdUsername(keyword);
        return sellerUsername;
    }
}
