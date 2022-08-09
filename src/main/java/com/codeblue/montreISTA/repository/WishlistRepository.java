package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query(value = "SELECT * FROM wishlist w WHERE wishlist_id=?1", nativeQuery = true)
    List<Wishlist> findByWishlistId(Long id);

    List<Wishlist> findByBuyerUserName(String keyword);

    List<Wishlist> findByBuyerUserUsername(String keyword);

    Optional<Wishlist> findFirstByBuyerBuyerIdOrderByCreatedAtDesc(Long id);
}
