package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.controller.WishlistController;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.WishlistRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.WishlistService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);
    private static final String Line = "====================";
    private final WishlistRepository wishlistRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final DTOConverter dtoConverter;

    @Override
    public ResponseEntity<Object> findAllWishlist() throws Exception {
        try {
            List<Wishlist> wishlists = wishlistRepository.findAllByOrderByWishlistIdAsc();
            if (wishlists.isEmpty()) {
                throw new Exception("wishlist not found");
            }
            List<WishlistResponseDTO> results = wishlists.stream().map(dtoConverter::convertWishlist).toList();
            logger.info("==================== Logger Start Get All Transactions     ====================");
            logger.info(String.valueOf(results));
            logger.info("==================== Logger End Get AlL Transactions   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "wishlist not found");
        }
    }

    @Override
    public ResponseEntity<Object> findWishlistById(Long id) throws Exception {
        try {
            Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new Exception("Wishlist not found"));
            WishlistResponseDTO result = dtoConverter.convertWishlist(wishlist);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully get wishlist by id", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "wishlish data not found!");
        }
    }

    @Override
    public ResponseEntity<Object> createWishlist(WishlistRequestDTO wishlist, Authentication authentication) throws Exception {
        try {
            Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please login as buyer"));
            Product product = productRepository.findById(wishlist.getProductId()).orElseThrow(() -> new Exception("Product not found"));
            if (buyer.getUser().getUserId().equals(product.getSeller().getUser().getUserId())) {
                throw new Exception("You can't order your own product");
            }
            Wishlist wishlistSave = wishlist.convertToEntity(buyer, product);
            Wishlist wishlistDTO = wishlistRepository.save(wishlistSave);
            WishlistResponseDTO result = dtoConverter.convertWishlist(wishlistDTO);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create wishlist", HttpStatus.CREATED, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "create wishlist failed!");
        }
    }

    @Override
    public ResponseEntity<Object> updateWishlist(WishlistRequestDTO wishlistRequestDTO, Long id, Authentication authentication) throws Exception {
        try {
            Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new Exception("Wishlist not found"));
            Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please login as buyer"));
            Product product = productRepository.findById(wishlistRequestDTO.getProductId()).orElseThrow(() -> new Exception("Product not found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkProduct = !product.getSeller().getUser().getUsername().equals(authentication.getName());
            boolean checkWishlist = wishlist.getBuyer().getUser().getUsername().equals(authentication.getName());
            boolean checkUser = checkProduct && checkWishlist;
            if (checkRoles || checkUser) {
                wishlist.setWishlistId(id);
                wishlist.setBuyer(buyer);
                wishlist.setProduct(product);
                wishlist.setQuantity(wishlistRequestDTO.getQuantity());
                Wishlist wishlistDTO = wishlistRepository.save(wishlist);
                WishlistResponseDTO result = dtoConverter.convertWishlist(wishlistDTO);
                logger.info(Line + "Logger Start Update By Id " + Line);
                logger.info(String.valueOf(result));
                logger.info(Line + "Logger End Update By Id " + Line);
                return ResponseHandler.generateResponse("successfully updated Wishlist", HttpStatus.CREATED, result);
            } else {
                throw new Exception("You can't edit other wishlist or order your own product");
            }
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "edit wishlist failed");
        }
    }

    @Override
    public ResponseEntity<Object> deleteWishlist(Long id, Authentication authentication) throws Exception {
        try {
            Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new Exception("Wishlist not found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkBuyer = wishlist.getBuyer().getUser().getUsername().equals(authentication.getName());
            if (checkRoles || checkBuyer) {
                wishlistRepository.deleteById(id);
                logger.info(Line + "Logger Start Delete By Id " + Line);
                logger.info("Delete Success");
                logger.info(Line + "Logger End Delete By Id " + Line);
                return ResponseHandler.generateResponse("successfully deleted wishlist", HttpStatus.OK, "success delete wishlist");

            } else {
                throw new Exception("You only can delete your wishlist");
            }
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "delete wishlist failed");
        }
    }

    @Override
    public ResponseEntity<Object> findByBuyerUserUsername(Authentication authentication) throws Exception {
        try {
            List<Wishlist> wishlists = wishlistRepository.findByBuyerUserUsername(authentication.getName());
            if (wishlists.isEmpty()) {
                throw new Exception("Please add Wishlist");
            }
            List<WishlistResponseDTO> results = wishlists.stream().map(dtoConverter::convertWishlist).toList();
            logger.info(Line + "Logger Start Get Buyer By Username " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Buyer By Username " + Line);
            return ResponseHandler.generateResponse("successfully get buyer wishlist", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "find wishlist failed");
        }
    }
}
