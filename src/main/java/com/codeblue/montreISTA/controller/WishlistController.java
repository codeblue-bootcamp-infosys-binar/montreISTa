package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
@Tag(name="06. Wishlist")
@SecurityRequirement(name = "bearer-key")
public class WishlistController {

    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    private static final String Line = "====================";
    private final WishlistService wishlistService;

    @GetMapping("/user/wishlist/my-wishlist")
    public ResponseEntity<Object> findByBuyerUserUsername(Authentication authentication) {
        try{
            List<WishlistResponseDTO> wishlists = wishlistService.findByBuyerUserUsername(authentication);
            logger.info(Line + "Logger Start Get Buyer By Username " + Line);
            logger.info(String.valueOf(wishlists));
            logger.info(Line + "Logger End Get Buyer By Username " + Line);
            return ResponseHandler.generateResponse("successfully get buyer wishlist", HttpStatus.OK, wishlists);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "find wishlist failed");
        }
    }

    //CREATE
    @PostMapping("/user/add-to-wishlist")
    public ResponseEntity<Object> createWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO,Authentication authentication)throws Exception{
        try {
            WishlistResponseDTO wishlist = wishlistService.createWishlist(wishlistRequestDTO,authentication);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(wishlist));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create wishlist", HttpStatus.CREATED, wishlist);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"create wishlist failed!");
        }
    }

    //UPDATE
    @PutMapping("/user/wishlist/edit/{id}")
    public ResponseEntity<Object> updateWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO, @PathVariable("id") Long id, Authentication authentication){
        try{
            WishlistResponseDTO wishlist = wishlistService.updateWishlist(wishlistRequestDTO, id, authentication);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(wishlist));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated Wishlist", HttpStatus.CREATED,wishlist );
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "edit wishlist failed");
        }
    }

    //DELETE
    @DeleteMapping("/user/wishlist/delete/{id}")
    public ResponseEntity<Object> deleteWishlist(@PathVariable("id") Long id, Authentication authentication){
        try{
            wishlistService.deleteWishlist(id, authentication);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted wishlist", HttpStatus.OK, "success delete wishlist");
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "delete wishlist failed");
        }

    }

    //GET ALL
    @GetMapping("/dashboard/wishlist")
    public ResponseEntity<Object> getAllWishlist(){
        try{
            List<WishlistResponseDTO> wishlists = wishlistService.findAllWishlist();
            logger.info("==================== Logger Start Get All Transactions     ====================");
            for(WishlistResponseDTO wishlistData : wishlists){
                Map<String, Object> wishlist = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Wishlist ID    : " + wishlistData.getWishlist_id());
                logger.info("Buyer ID       : " + wishlistData.getBuyer_id());
                logger.info("Quantity       : " + wishlistData.getQuantity());
                logger.info("Product ID     : " + wishlistData.getProduct());
                wishlist.put("Wishlist ID   ",    wishlistData.getWishlist_id());
                wishlist.put("Buyer ID      ",    wishlistData.getBuyer_id());
                wishlist.put("Quantity      ",    wishlistData.getQuantity());
                wishlist.put("Product ID    ",    wishlistData.getProduct());
            }
            logger.info("==================== Logger End Get AlL Transactions   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, wishlists);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "wishlist not found");
        }
    }

    //GET ONE BY ID
    @GetMapping("/dashboard/wishlist/{id}")
    public ResponseEntity<Object> getWishlistById(@PathVariable("id") Long id){
        try{
            WishlistResponseDTO wishlist = wishlistService.findWishlistById(id);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(wishlist));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully get wishlist by id", HttpStatus.OK, wishlist);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "wishlish data not found!");
        }
    }

}