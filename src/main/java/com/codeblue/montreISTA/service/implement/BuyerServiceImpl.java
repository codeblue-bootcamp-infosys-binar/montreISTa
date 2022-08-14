package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.BuyerRequestDTO;
import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.service.BuyerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private BuyerRepository buyerRepository;
    private UserRepository userRepository;


    @Override
    public List<BuyerResponseDTO> findAllBuyer() {
        return buyerRepository.findAllByOrderByBuyerIdAsc().stream().map(Buyer::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BuyerResponseDTO findBuyerById(Long id) throws Exception{
        return buyerRepository.findById(id).orElseThrow(()->new Exception("Buyer not found")).convertToResponse(); }

    @Override
    public BuyerResponseDTO createBuyer(Authentication authentication) throws Exception {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("Buyer not found"));
//        User user = userRepository.findById(buyer.getUser_id()).orElseThrow(()->new Exception("Buyer not found"));
        Optional<Buyer> buyerUser = buyerRepository.findByUserUserId(user.getUserId());
        if (buyerUser.isPresent()){
            throw new Exception("User have buyer id");
        }
        Buyer buyer = new Buyer();
        buyer.setUser(user);
        return buyerRepository.save(buyer).convertToResponse();
    }

    @Override
    public BuyerResponseDTO updateBuyer(BuyerRequestDTO buyer,Long id)throws Exception {
        Buyer targetBuyer = buyerRepository.findById(id).orElseThrow(()->new Exception("Buyer not found"));
        User user = userRepository.findById(buyer.getUser_id()).orElseThrow(() -> new Exception("Buyer not found"));
        return buyerRepository.save(buyer.convertToEntity(user)).convertToResponse();
    }


    @Override
    public void deleteBuyer(Long id) { buyerRepository.deleteById(id); }

    @Override
    public List<BuyerResponseDTO> findByUsername(String keywoard) {
        return buyerRepository.findByUserUsername(keywoard).stream().map(Buyer::convertToResponse)
                .collect(Collectors.toList());
    }
}