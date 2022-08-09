package com.codeblue.montreISTA.controller;



import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.WishlistServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class WishlistController {

    @Autowired
    WishlistServiceImpl wishlistServiceImpl;

    //GET ALL
    @GetMapping("/wishlist")
    public ResponseEntity<Object> getAllWishlist(){
        try{
            List<Wishlist> wishlists = wishlistServiceImpl.findAllWishlist();

            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, wishlists);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ALL BY SELLER ID
    @GetMapping("/wishlist/store/{wishlist_id}")
    public ResponseEntity<Object> getAllWishlistByWishlistId(@PathVariable("wishlist_id") Long wishlistId){
        try{
            List<Wishlist> wishlist = wishlistServiceImpl.findWishlisttByWishlistId(wishlistId);
            return ResponseHandler.generateResponse("successfully retrieved wishlists", HttpStatus.OK, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/wishlist/buyer/username")
    public ResponseEntity<Object> findByBuyerUsername(@Param("keyword") String keyword){
        try{
            List<Wishlist> wishlists = wishlistServiceImpl.findByBuyerUserName(keyword);

            return ResponseHandler.generateResponse("successfully retrieved buyer username", HttpStatus.OK, wishlists);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/wishlist/Buyer/User/username")
    public ResponseEntity<Object> findByBuyerUserUsername(@Param("keyword") String keyword){
        try{
            List<Wishlist> wishlists = wishlistServiceImpl.findByBuyerUserUsername(keyword);

            return ResponseHandler.generateResponse("successfully retrieved buyer username", HttpStatus.OK, wishlists);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/wishlist/{id}")
    public ResponseEntity<Object> getWishlistById(@PathVariable("id") Long id){
        try{
            Optional<Wishlist> wishlist = wishlistServiceImpl.findWishlistById(id);
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/wishlist/create")
    public ResponseEntity<Object> createWishlist(@RequestBody Wishlist wishlistRequestDTO){
        try {
            Wishlist wishlist = wishlistServiceImpl.createWishlist(wishlistRequestDTO);
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.CREATED, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/wishlist/update/{id}")
    public ResponseEntity<Object> updateWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO, @PathVariable("id") Long id){
        try{
            Optional<Wishlist> targetWishlist = wishlistServiceImpl.findWishlistById(id);
            Wishlist updateWishlist = targetWishlist.get();
            updateWishlist.setWishlistId(id);
            updateWishlist.setBuyer(wishlistRequestDTO.getBuyer());
            updateWishlist.setProduct(wishlistRequestDTO.getProduct());

            wishlistServiceImpl.updateWishlist(updateWishlist);
            return ResponseHandler.generateResponse("successfully updated Wishlist", HttpStatus.CREATED, updateWishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/wishlist/delete/{id}")
    public ResponseEntity<Object> deleteWishlist(@PathVariable("id") Long id){
        try{
            wishlistServiceImpl.deleteWishlist(id);
            return ResponseHandler.generateResponse("successfully deleted wishlist", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

}
