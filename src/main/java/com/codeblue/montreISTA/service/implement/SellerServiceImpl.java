package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.LoginSellerResponseDTO;
import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.helper.Pagination;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.SellerRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final RoleRepository roleRepository;


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
    public SellerResponseDTO createSeller(SellerRequestDTO sellerRequestDTO, Authentication authentication) throws Exception  {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("Please sign up"));
        Optional<Seller> sellerOptional = sellerRepository.findByUserUserId(user.getUserId());
        Optional<Seller> sellerValidation = sellerRepository.findByStoreName(sellerRequestDTO.getStoreName());
        if(sellerOptional.isPresent()) {
            throw new Exception("You only can have 1 store");
        }
        if(sellerValidation.isPresent()){
            throw new Exception("Your Store Name has been used");
        }
        Seller seller = sellerRequestDTO.convertToEntity(user);
        seller.setStorePhoto("https://www.shutterstock.com/image-vector/shop-icon-store-230619400");
        Seller sellerDTO = sellerRepository.save(seller);
        return sellerDTO.convertToResponse();
    }
    @Override
    public SellerResponseDTO updateSeller(SellerRequestDTO seller, Authentication authentication)throws Exception {
        Seller sellerUpdate = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as seller before update seller info"));
        sellerUpdate.setStoreAddress(seller.getStoreAddress());
        sellerUpdate.setStoreName(seller.getStoreName());
        return sellerRepository.save(sellerUpdate).convertToResponse();
    }

    @Override
    public SellerResponseDTO uploadPhotoStore(Authentication authentication, MultipartFile file) throws Exception {
        Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as seller"));
        String url = cloudinaryService.uploadFile(file);
        seller.setStorePhoto(url);
        Seller sellerSave = sellerRepository.save(seller);
        return sellerSave.convertToResponse();
    }

    @Override
    public LoginSellerResponseDTO loginAsSeller(String keyword,Integer page, String sort, boolean descending) throws Exception {
        Pageable pageable = Pagination.paginate(page, sort, descending);
        Seller seller = sellerRepository.findByUserUsername(keyword).orElseThrow(()->new Exception("Create your shop first"));
        List<Product> products = productRepository.findBySellerUserNameIgnoreCaseContaining(keyword,pageable);
        return DTOConverter.convertLoginSeller(seller,products);
    }

    @Override
    public void deleteSeller(Long id,Authentication authentication) throws Exception{
        Seller seller = sellerRepository.findById(id).orElseThrow(()->new Exception("Seller not found"));
        List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
        boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
        boolean checkUser = seller.getUser().getUsername().equals(authentication.getName());
        if (checkRoles || checkUser){
            sellerRepository.deleteById(id);
        } else {
            throw new Exception("You can't delete other seller");
        }
    }


    @Override
    public SellerResponseDTO findByUsername(String keyword)throws Exception {
        Seller seller = sellerRepository.findByUserUsername(keyword).orElseThrow(()->new Exception("Create your shop first"));
        return seller.convertToResponse();
    }


}
