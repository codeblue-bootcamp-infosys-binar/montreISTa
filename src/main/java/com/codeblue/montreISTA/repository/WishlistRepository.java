package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query(value = "SELECT * FROM wishlist w WHERE wishlist_id=?1", nativeQuery = true)
    List<Wishlist> findByWishlistId(Long id);

}
