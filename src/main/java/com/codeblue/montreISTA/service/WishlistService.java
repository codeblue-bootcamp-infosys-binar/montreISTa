package com.codeblue.montreISTA.service;



import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    public List<Wishlist> findAllWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findAll();
        return wishlists;
    }

    public Optional<Wishlist> findWishlistById(Long id) {
        return wishlistRepository.findById(id);
    }

    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public Wishlist updateWishlist(Wishlist updateWishlist) {
        return wishlistRepository.save(updateWishlist);
    }

    public void deleteWishlist(Long id) {wishlistRepository.deleteById(id);
    }

    public List<Wishlist> findWishlisttByWishlistId(Long id) {
        List<Wishlist> wishlist = wishlistRepository.findByWishlistId(id);
        if(wishlist.isEmpty()){
            return null;
        } else {
            return wishlist;
        }
    }


    public List<Wishlist> findByBuyerUsername(String keyword) {
        List<Wishlist> wishlistBuyerUsername = wishlistRepository.findByBuyerUsername(keyword);
        return wishlistBuyerUsername;
    }

    public List<Wishlist> findByBuyerUserUsername(String keyword) {
        List<Wishlist> wishlistBuyerUserUsername = wishlistRepository.findByBuyerUserUsername(keyword);
        return wishlistBuyerUserUsername;
    }
}
