package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
