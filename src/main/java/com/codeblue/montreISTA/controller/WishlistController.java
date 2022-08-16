package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@AllArgsConstructor
@Tag(name="06. Wishlist")
@SecurityRequirement(name = "bearer-key")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/user/wishlist/my-wishlist")
    public ResponseEntity<Object> findByBuyerUserUsername(Authentication authentication) {
        try{
            List<WishlistResponseDTO> wishlists = wishlistService.findByBuyerUserUsername(authentication);
            return ResponseHandler.generateResponse("successfully retrieved buyer username", HttpStatus.OK, wishlists);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/user/addToWishlist")
    public ResponseEntity<Object> createWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO,Authentication authentication)throws Exception{
        try {
            WishlistResponseDTO wishlist = wishlistService.createWishlist(wishlistRequestDTO,authentication);
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.CREATED, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/user/wishlist/edit/{id}")
    public ResponseEntity<Object> updateWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO, @PathVariable("id") Long id, Authentication authentication){
        try{
            WishlistResponseDTO wishlist = wishlistService.updateWishlist(wishlistRequestDTO, id, authentication);
            return ResponseHandler.generateResponse("successfully updated Wishlist", HttpStatus.CREATED,wishlist );
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/user/wishlist/delete/{id}")
    public ResponseEntity<Object> deleteWishlist(@PathVariable("id") Long id, Authentication authentication){
        try{
            wishlistService.deleteWishlist(id, authentication);
            return ResponseHandler.generateResponse("successfully deleted wishlist", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    //GET ALL
    @GetMapping("/dashboard/wishlist")
    public ResponseEntity<Object> getAllWishlist(){
        try{
            List<WishlistResponseDTO> wishlists = wishlistService.findAllWishlist();
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, wishlists);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/dashboard/wishlist/{id}")
    public ResponseEntity<Object> getWishlistById(@PathVariable("id") Long id){
        try{
            WishlistResponseDTO wishlist = wishlistService.findWishlistById(id);


            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}