package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.LoginSellerResponseDTO;
import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.controller.SellerController;
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
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.SellerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

    private static final Logger logger = LoggerFactory.getLogger(SellerController.class);
    private static final String Line = "====================";

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final RoleRepository roleRepository;
    private final DTOConverter dtoConverter;
    @Override
    public ResponseEntity<Object> findAllSeller() {
        try {
            List<SellerResponseDTO> sellers = sellerRepository.findByOrderBySellerIdAsc().stream()
                    .map(Seller::convertToResponse)
                    .collect(Collectors.toList());
            logger.info("==================== Logger Start Get All Sellers     ====================");
            for (SellerResponseDTO sellerData : sellers) {
                logger.info("-------------------------");
                logger.info("Seller ID       : " + sellerData.getSellerId());
                logger.info("Store Address   : " + sellerData.getStore_address());
                logger.info("Store Name      : " + sellerData.getStore_name());
                logger.info("Store Photo     : " + sellerData.getStore_photo());
            }
            logger.info("==================== Logger End Get All Sellers    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully get all sellers", HttpStatus.OK, sellers);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Sellers     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Seller had no value!")));
            logger.info("==================== Logger End Get All Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Seller had no value!");
        }

    }

    @Override
    public ResponseEntity<Object> findSellerById(Long id) throws Exception {
        try{
        SellerResponseDTO seller = sellerRepository.findById(id).orElseThrow(() -> new Exception("Seller Not Found")).convertToResponse();
        logger.info(Line + "Logger Start Get store id " + Line);
        logger.info(String.valueOf(seller));
        logger.info(Line + "Logger End Get store id " + Line);
        return ResponseHandler.generateResponse("successfully Get Seller", HttpStatus.OK, seller);
    } catch (Exception e) {
        logger.error(Line + " Logger Start Error " + Line);
        logger.error(e.getMessage());
        logger.error(Line + " Logger End Error " + Line);
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Seller not Found");
    }
    }

    @Override
    public ResponseEntity<Object> createSeller(SellerRequestDTO sellerRequestDTO, Authentication authentication) throws Exception {
        try {
            User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new Exception("Please sign up"));
            Optional<Seller> sellerOptional = sellerRepository.findByUserUserId(user.getUserId());
            Optional<Seller> sellerValidation = sellerRepository.findByStoreName(sellerRequestDTO.getStoreName());
            if (sellerOptional.isPresent()) {
                throw new Exception("You only can have 1 store");
            }
            if (sellerValidation.isPresent()) {
                throw new Exception("Your Store Name has been used");
            }
            Seller seller = sellerRequestDTO.convertToEntity(user);
            seller.setStorePhoto("https://www.shutterstock.com/image-vector/shop-icon-store-230619400");
            Seller sellerDTO = sellerRepository.save(seller);
            SellerResponseDTO result = sellerDTO.convertToResponse();
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully login as seller", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create seller!");
        }
    }

    @Override
    public ResponseEntity<Object> updateSeller(SellerRequestDTO seller, Authentication authentication) throws Exception {
        try {
            Seller sellerUpdate = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please login as seller before update seller info"));
            sellerUpdate.setStoreAddress(seller.getStoreAddress());
            sellerUpdate.setStoreName(seller.getStoreName());
            SellerResponseDTO result = sellerRepository.save(sellerUpdate).convertToResponse();
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated Seller", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update seller!");
        }
    }

    @Override
    public ResponseEntity<Object> uploadPhotoStore(Authentication authentication, MultipartFile file) throws Exception {
        try {
            Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please login as seller"));
            String url = cloudinaryService.uploadFile(file);
            seller.setStorePhoto(url);
            Seller sellerSave = sellerRepository.save(seller);
            SellerResponseDTO result = sellerSave.convertToResponse();
            logger.info(Line + "Logger Start Update Photo " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Update Photo " + Line);
            return ResponseHandler.generateResponse("Success upload photo profile", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "failed update photo");
        }
    }

    @Override
    public ResponseEntity<Object> loginAsSeller(String keyword, Integer page, String sort, boolean descending) throws Exception {
        try {
            Pageable pageable = Pagination.paginate(page, sort, descending);
            Seller seller = sellerRepository.findByUserUsername(keyword).orElseThrow(() -> new Exception("Create your shop first"));
            List<Product> products = productRepository.findBySellerUserNameIgnoreCaseContaining(keyword, pageable);
            LoginSellerResponseDTO result = dtoConverter.convertLoginSeller(seller,products);
            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Seller not Found");
        }
    }

    @Override
    public ResponseEntity<Object> deleteSeller(Long id, Authentication authentication) throws Exception {
        try {
            Seller seller = sellerRepository.findById(id).orElseThrow(() -> new Exception("Seller not found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkUser = seller.getUser().getUsername().equals(authentication.getName());
            if (checkRoles || checkUser) {
                sellerRepository.deleteById(id);
            } else {
                throw new Exception("You can't delete other seller");
            }
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("Success Delete", HttpStatus.OK, null);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Delete Seat");
        }
    }


    @Override
    public ResponseEntity<Object> findByUsername(String keyword) throws Exception {
        try {
            Seller seller = sellerRepository.findByUserUsername(keyword).orElseThrow(() -> new Exception("Create your shop first"));
            SellerResponseDTO result = seller.convertToResponse();
            logger.info(Line + "Logger Start Get seller id " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Get seller id " + Line);
            return ResponseHandler.generateResponse("success get seller username", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "seller not found!");
        }
    }


}
