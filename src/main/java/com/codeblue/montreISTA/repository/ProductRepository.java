package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
//    @Query(value = "SELECT * FROM products p WHERE seller_id=?1", nativeQuery = true)
    List<Product> findBySellerSellerId(Long id);

    List<Product> findByCategoriesCategoryCategoriesId(Long id);

    List<Product> findAllByOrderByCreatedAtAsc();

    Optional<Product> findFirstBySellerUserIdUsernameOrderByCreatedAtDesc(String keyword);
    
    List<Product> findBySellerUserIdNameIgnoreCaseContaining(String name);

    List<Product> findBySellerStoreNameIgnoreCaseContaining(String name);

    List<Product> findByProductNameIgnoreCaseContaining(String name);

}
