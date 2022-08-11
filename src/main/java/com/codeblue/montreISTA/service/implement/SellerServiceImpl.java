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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

    private UserRepository userRepository;
    private SellerRepository sellerRepository;

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
    public SellerResponseDTO createSeller(SellerRequestDTO seller) throws Exception  {
        User user = userRepository.findById(seller.getUserId()).orElseThrow(()->new Exception("Seller Not Found"));
        Optional<Seller> sellerOptional = sellerRepository.findByUserIdUserId(seller.getUserId());
        if(sellerOptional.isPresent()){
            throw new Exception("User has been seller");
        }
        return sellerRepository.save(seller.convertToEntity(user)).convertToResponse();
    }
    @Override
    public SellerResponseDTO updateSeller(SellerRequestDTO seller,Long id)throws Exception {
        User user = userRepository.findById(seller.getUserId()).orElseThrow(()->new Exception("User Not Found"));
        Seller sellerUpdate = sellerRepository.findById(id).orElseThrow(()->new Exception("Seller Not Found"));
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
