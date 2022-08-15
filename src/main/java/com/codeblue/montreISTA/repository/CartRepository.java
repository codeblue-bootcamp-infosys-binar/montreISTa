package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findAllByOrderByCartIdAsc();
    List<Cart> findByBuyerUserUsernameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
    List<Cart> findByBuyerBuyerIdOrderByCartIdDesc(long id);
    List<Cart> findByProductSellerUserIdUsernameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
    List<Cart> findByProductProductNameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
    List<Cart> findByProductCategoriesCategoryNameIgnoreCaseContainingOrderByCartIdAsc(String keyword);
}
