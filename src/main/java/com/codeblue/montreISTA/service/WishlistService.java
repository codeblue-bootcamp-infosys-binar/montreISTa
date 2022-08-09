package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {

    List<Wishlist> findAllWishlist();

    Optional<Wishlist> findWishlistById(Long id);

    Wishlist createWishlist(Wishlist wishlist);

    Wishlist updateWishlist(Wishlist updateWishlist);

    void deleteWishlist(Long id);

    List<Wishlist> findWishlisttByWishlistId(Long id);

    List<Wishlist> findByBuyerUserName(String keyword);

     List<Wishlist> findByBuyerUserUsername(String keyword);
}
