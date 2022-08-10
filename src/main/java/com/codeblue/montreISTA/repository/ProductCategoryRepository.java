package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByProductProductNameIgnoreCaseContaining(String name);
    List<ProductCategory> findByCategoryNameIgnoreCaseContaining(String name);
    List<ProductCategory> findByCategoryCategoriesId(Long id);
    List<ProductCategory> findByProductProductId(Long id);
}
