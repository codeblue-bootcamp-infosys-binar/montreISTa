package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByBuyerUserNameContaining(String keyword);
    List<Cart> findByProductSellerUserIdNameContaining(String keyword);
    List<Cart> findByProductProductNameContaining(String keyword);
    List<Cart> findByProductCategoriesCategoryNameContaining(String keyword);
}
