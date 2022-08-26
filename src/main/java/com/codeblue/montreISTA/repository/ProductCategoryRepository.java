package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByProductProductNameIgnoreCaseContaining(String name);
    List<ProductCategory> findByCategoryNameIgnoreCaseContaining(String name);
    List<ProductCategory> findByCategoryNameIgnoreCase(String keyword);
    List<ProductCategory> findByCategoryCategoriesId(Long id);
    List<ProductCategory> findByProductSellerUserUsernameIgnoreCaseContaining(String name);
    List<ProductCategory> findByProductId(Long id);
    @Query("SELECT p FROM ProductCategory p WHERE UPPER(CONCAT(p.product.productName, ' ', " +
            "p.product.description, ' ', " +
            "p.product.price, ' '," +
            "p.category.name, ' ',p.product.seller.storeName, ' ')) LIKE %:name%")
    Page<ProductCategory> search(@Param("name")String name, Pageable pageable);
}
