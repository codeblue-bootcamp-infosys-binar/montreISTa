package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findAllByOrderByCartIdAsc();
    List<Cart> findByBuyerUserNameIgnoreCaseContaining(String keyword);
    List<Cart> findByProductSellerUserIdNameIgnoreCaseContaining(String keyword);
    List<Cart> findByProductProductNameIgnoreCaseContaining(String keyword);
    List<Cart> findByProductCategoriesCategoryNameIgnoreCaseContaining(String keyword);
}
