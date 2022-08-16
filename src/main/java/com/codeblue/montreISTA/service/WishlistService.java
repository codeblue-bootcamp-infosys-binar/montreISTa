package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.entity.Wishlist;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface WishlistService {

    List<WishlistResponseDTO> findAllWishlist() throws Exception;

    WishlistResponseDTO findWishlistById(Long id) throws Exception;

    WishlistResponseDTO createWishlist(WishlistRequestDTO wishlist, Authentication authentication)throws Exception;

    WishlistResponseDTO updateWishlist(WishlistRequestDTO updateWishlist, Long id, Authentication authentication)throws Exception;

    void deleteWishlist(Long id,Authentication authentication) throws Exception;

    List<Wishlist> findByBuyerUserName(String keyword);

    List<WishlistResponseDTO> findByBuyerUserUsername(Authentication authentication) throws Exception;
}