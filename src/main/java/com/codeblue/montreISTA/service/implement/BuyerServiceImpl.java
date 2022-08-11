package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.BuyerRequestDTO;
import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.service.BuyerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private BuyerRepository buyerRepository;
    private UserRepository userRepository;


    @Override
    public List<Buyer> findAllBuyer() {
        return buyerRepository.findAllByOrderByBuyerIdAsc();
    }

    @Override
    public Optional<Buyer> findBuyerById(Long id) { return buyerRepository.findById(id); }

    @Override
    public BuyerResponseDTO createBuyer(BuyerRequestDTO buyer) throws Exception {
        Optional<User> userOptional = userRepository.findById(buyer.getUser_id());
        Optional<Buyer> buyerUser = buyerRepository.findByUserUserId(buyer.getUser_id());
        if (buyerUser.isPresent() || userOptional.isEmpty()){
            throw new Exception("User have buyer id or user not found");
        }
        Buyer buyersave = buyerRepository.save(buyer.convertToEntity(userOptional.get()));
        return buyersave.convertToResponse();
    }

    @Override
    public Buyer updateBuyer(Buyer updateBuyer) { return buyerRepository.save(updateBuyer); }


    @Override
    public void deleteBuyer(Long id) { buyerRepository.deleteById(id); }


    @Override
    public List<Buyer> findBuyerByBuyerId(Long id) {
        List<Buyer> buyer = buyerRepository.findByBuyerId(id);
        if (buyer.isEmpty()){
            return  null;
        } else {
            return buyer;
        }
    }

    public List<Buyer> findByUsername(String keywoard) {
        List<Buyer> buyerUsername = buyerRepository.findByUserUsername(keywoard);
        return buyerUsername;
    }
}