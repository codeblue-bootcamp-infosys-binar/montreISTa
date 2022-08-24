package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.entity.Wishlist;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface WishlistService {

    ResponseEntity<Object> findAllWishlist() throws Exception;

    ResponseEntity<Object> findWishlistById(Long id) throws Exception;

    ResponseEntity<Object> createWishlist(WishlistRequestDTO wishlist, Authentication authentication)throws Exception;

    ResponseEntity<Object> updateWishlist(WishlistRequestDTO updateWishlist, Long id, Authentication authentication)throws Exception;

    ResponseEntity<Object> deleteWishlist(Long id,Authentication authentication) throws Exception;

    ResponseEntity<Object> findByBuyerUserUsername(Authentication authentication) throws Exception;
}