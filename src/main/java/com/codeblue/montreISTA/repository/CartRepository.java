package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findAllByOrderByCartIdAsc();
    List<Cart> findByBuyerUserNameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
    List<Cart> findByProductSellerUserIdNameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
    List<Cart> findByProductProductNameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
    List<Cart> findByProductCategoriesCategoryNameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
}
