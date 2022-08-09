package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.SellerRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Override
    public List<Seller> findAllSeller() {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers;
    }

    @Override
    public Optional<Seller> findSellerById(Long id) {
        return sellerRepository.findById(id);
    }


    @Override
    public Seller createSeller(SellerRequestDTO seller)  {
        Optional<User> userOptional = userRepository.findById(seller.getUserId());
        User user = userOptional.get();
        Seller sellerSave = seller.convertToEntity(user);
         return sellerRepository.save(sellerSave);
    }

    @Override
    public Seller updateSeller(Seller updateSeller) {
        return sellerRepository.save(updateSeller);
    }


    @Override
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }


    @Override
    public List<Seller> findSellertBySellerId(Long id) {
        List<Seller> seller = sellerRepository.findBySellerId(id);
        if(seller.isEmpty()){
            return null;
        } else {
            return seller;
        }
    }

    @Override
    public List<Seller> findByUsername(String keyword) {
        List<Seller> sellerUsername = sellerRepository.findByUserIdUsername(keyword);
        return sellerUsername;
    }
}
