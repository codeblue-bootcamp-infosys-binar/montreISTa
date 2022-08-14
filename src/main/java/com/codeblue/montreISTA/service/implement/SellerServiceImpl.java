package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.SellerRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Override
    public List<SellerResponseDTO> findAllSeller() {
      return sellerRepository.findByOrderBySellerIdAsc().stream()
              .map(Seller::convertToResponse)
              .collect(Collectors.toList());

    }
    @Override
    public SellerResponseDTO findSellerById(Long id) throws Exception{
        return sellerRepository.findById(id).orElseThrow(()->new Exception("Seller Not Found")).convertToResponse();
    }

    @Override
    public SellerResponseDTO createSeller(SellerRequestDTO seller, Authentication authentication) throws Exception  {
        if(authentication==null){
            throw new Exception("Please login");
        }
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("Please sign up"));
        Optional<Seller> sellerOptional = sellerRepository.findByUserIdUserId(user.getUserId());
        if(sellerOptional.isPresent()){
            return sellerOptional.get().convertToResponse();
        }else {
            Seller sellerDTO = sellerRepository.save(seller.convertToEntity(user));
            return sellerDTO.convertToResponse();
        }
    }
    @Override
    public SellerResponseDTO updateSeller(SellerRequestDTO seller, Authentication authentication)throws Exception {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("Please sign up"));
        Seller sellerUpdate = sellerRepository.findById(user.getUserId()).orElseThrow(()->new Exception("Please login as seller before update seller info"));
        sellerUpdate.setUserId(user);
        sellerUpdate.setStoreAddress(seller.getStoreAddress());
        sellerUpdate.setStoreName(seller.getStoreName());
        sellerUpdate.setStorePhoto(seller.getStorePhoto());
        return sellerRepository.save(sellerUpdate).convertToResponse();
    }

    @Override
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }


    @Override
    public List<SellerResponseDTO> findByUsername(String keyword) {
        return sellerRepository.findByUserIdUsername(keyword).stream()
                .map(Seller::convertToResponse)
                .collect(Collectors.toList());
    }


}
