package com.codeblue.montreISTA.controller;



import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    //GET ALL
    @GetMapping("/wishlist")
    public ResponseEntity<Object> getAllWishlist(){
        try{
            List<Wishlist> wishlists = wishlistService.findAllWishlist();

            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, wishlists);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ALL BY SELLER ID
    @GetMapping("/wishlist/store/{wishlist_id}")
    public ResponseEntity<Object> getAllWishlistByWishlistId(@PathVariable("wishlist_id") Long wishlistId){
        try{
            List<Wishlist> wishlist = wishlistService.findWishlisttByWishlistId(wishlistId);
            return ResponseHandler.generateResponse("successfully retrieved wishlists", HttpStatus.OK, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/wishlist/{id}")
    public ResponseEntity<Object> getWishlistById(@PathVariable("id") Long id){
        try{
            Optional<Wishlist> wishlist = wishlistService.findWishlistById(id);
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.OK, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/wishlist/create")
    public ResponseEntity<Object> createWishlist(@RequestBody Wishlist newWishlist){
        try {
            Wishlist wishlist = wishlistService.createWishlist(newWishlist);
            return ResponseHandler.generateResponse("successfully retrieved wishlist", HttpStatus.CREATED, wishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/wishlist/update/{id}")
    public ResponseEntity<Object> updateWishlist(@RequestBody Wishlist wishlist, @PathVariable("id") Long id){
        try{
            Optional<Wishlist> targetWishlist = wishlistService.findWishlistById(id);
            Wishlist updateWishlist = targetWishlist.get();
            updateWishlist.setWishlistId(id);
            updateWishlist.setBuyer(wishlist.getBuyer());
            updateWishlist.setProduct(wishlist.getProduct());

            wishlistService.updateWishlist(updateWishlist);
            return ResponseHandler.generateResponse("successfully updated Wishlist", HttpStatus.CREATED, updateWishlist);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/wishlist/delete/{id}")
    public ResponseEntity<Object> deleteWishlist(@PathVariable("id") Long id){
        try{
            wishlistService.deleteWishlist(id);
            return ResponseHandler.generateResponse("successfully deleted wishlist", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

}
