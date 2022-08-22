package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.service.BuyerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final BuyerRepository buyerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<BuyerResponseDTO> findAllBuyer() {
        return buyerRepository.findAllByOrderByBuyerIdAsc().stream().map(Buyer::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BuyerResponseDTO findBuyerById(Long id) throws Exception{
        return buyerRepository.findById(id).orElseThrow(()->new Exception("Buyer not found")).convertToResponse(); }

    @Override
    public List<ProductResponseDTO> createBuyer(Authentication authentication) throws Exception {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("User not found"));
        Optional<Buyer> buyerUser = buyerRepository.findByUserUsername(authentication.getName());
        if (buyerUser.isEmpty()) {
            Buyer buyer = new Buyer();
            buyer.setUser(user);
            buyerRepository.save(buyer);
        }
        List<Product> products = productRepository.findAllByOrderByCreatedAtAsc();
        return DTOConverter.convertProducts(products);
    }


    @Override
    public void deleteBuyer(Long id,Authentication authentication) throws Exception {
        Buyer buyer = buyerRepository.findById(id).orElseThrow(()->new Exception("Buyer not found"));
        List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
        boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
        boolean checkUser = buyer.getUser().getUsername().equals(authentication.getName());
        if (checkRoles || checkUser){
            buyerRepository.deleteById(id);
        } else {
            throw new Exception("You can't delete other buyer");
        }

        buyerRepository.deleteById(id);
    }

    @Override
    public List<BuyerResponseDTO> findByUsername(String keyword) throws Exception{
        List<BuyerResponseDTO> results = buyerRepository.findByUserUsername(keyword).stream().map(Buyer::convertToResponse)
                .collect(Collectors.toList());
        if(results.isEmpty()){
            throw new Exception("Buyer not found");
        }
        return results;
    }
}