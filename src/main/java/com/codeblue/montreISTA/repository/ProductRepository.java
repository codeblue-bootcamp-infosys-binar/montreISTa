package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
//    @Query(value = "SELECT * FROM products p WHERE seller_id=?1", nativeQuery = true)
    List<Product> findBySellerSellerId(Long id, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    List<Product> findByCategoriesCategoryCategoriesId(Long id, Pageable pageable);
    List<Product> findByCategoriesCategoryNameIgnoreCaseContaining(String name, Pageable pageable);
    Optional<Product> findFirstBySellerUserUsernameOrderByIdDesc(String username);
    
    List<Product> findBySellerUserUsernameIgnoreCaseContaining(String name, Pageable pageable);

    List<Product> findBySellerStoreNameIgnoreCaseContaining(String name, Pageable pageable);

    List<Product> findByProductNameIgnoreCaseContaining(String name, Pageable pageable);

}
