package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> findAll();
    List<ProductCategory> findByProductName(String keyword)throws Exception;
    List<ProductCategory> findByCategoryName(String keyword)throws Exception;

    ProductCategory createProductCategory(ProductCategory productCategory);
    ProductCategory updateProductCategory(ProductCategory productCategory,Long id)throws Exception;

    void deleteById(Long id)throws Exception;
}
