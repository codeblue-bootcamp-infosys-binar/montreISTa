package com.codeblue.montreISTA.service.implement;



import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.repository.WishlistRepository;
import com.codeblue.montreISTA.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    @Override
    public List<Wishlist> findAllWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findAll();
        return wishlists;
    }

    @Override
    public Optional<Wishlist> findWishlistById(Long id) {
        return wishlistRepository.findById(id);
    }

    @Override
    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist updateWishlist(Wishlist updateWishlist) {
        return wishlistRepository.save(updateWishlist);
    }

    @Override
    public void deleteWishlist(Long id) {wishlistRepository.deleteById(id);
    }

    @Override
    public List<Wishlist> findWishlisttByWishlistId(Long id) {
        List<Wishlist> wishlist = wishlistRepository.findByWishlistId(id);
        if(wishlist.isEmpty()){
            return null;
        } else {
            return wishlist;
        }
    }




    @Override
    public List<Wishlist> findByBuyerUserName(String keyword) {
        List<Wishlist> wishlistBuyerUsername = wishlistRepository.findByBuyerUserName(keyword);
        return wishlistBuyerUsername;
    }

    @Override
    public List<Wishlist> findByBuyerUserUsername(String keyword) {
        List<Wishlist> wishlistBuyerUserUsername = wishlistRepository.findByBuyerUserUsername(keyword);
        return wishlistBuyerUserUsername;
    }
}
