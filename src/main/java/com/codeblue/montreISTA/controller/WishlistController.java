package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.service.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;




@RestController
@AllArgsConstructor
@Tag(name="06. Wishlist")
@SecurityRequirement(name = "bearer-key")
public class WishlistController {


    private final WishlistService wishlistService;

    @GetMapping("/user/wishlist/my-wishlist")
    public ResponseEntity<Object> findByBuyerUserUsername(Authentication authentication) throws Exception {
            return wishlistService.findByBuyerUserUsername(authentication);
    }

    //CREATE
    @PostMapping("/user/add-to-wishlist")
    public ResponseEntity<Object> createWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO,Authentication authentication)throws Exception{
           return wishlistService.createWishlist(wishlistRequestDTO,authentication);
    }

    //UPDATE
    @PutMapping("/user/wishlist/edit/{id}")
    public ResponseEntity<Object> updateWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO, @PathVariable("id") Long id, Authentication authentication) throws Exception {
            return wishlistService.updateWishlist(wishlistRequestDTO, id, authentication);
    }

    //DELETE
    @DeleteMapping("/user/wishlist/delete/{id}")
    public ResponseEntity<Object> deleteWishlist(@PathVariable("id") Long id, Authentication authentication) throws Exception {
         return  wishlistService.deleteWishlist(id, authentication);
    }

    //GET ALL
    @GetMapping("/dashboard/wishlist")
    public ResponseEntity<Object> getAllWishlist() throws Exception {
            return wishlistService.findAllWishlist();
    }

    //GET ONE BY ID
    @GetMapping("/dashboard/wishlist/{id}")
    public ResponseEntity<Object> getWishlistById(@PathVariable("id") Long id) throws Exception {
           return wishlistService.findWishlistById(id);
    }

}