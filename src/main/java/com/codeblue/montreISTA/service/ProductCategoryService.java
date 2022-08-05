package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ProductCategoryRequestDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryResponseDTO;
import com.codeblue.montreISTA.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryResponseDTO> findAll();
    List<ProductCategoryResponseDTO> findByProductName(String keyword)throws Exception;
    List<ProductCategoryResponseDTO> findByCategoryName(String keyword)throws Exception;

    ProductCategoryResponseDTO createProductCategory(ProductCategoryRequestDTO productCategory);
    ProductCategoryResponseDTO updateProductCategory(ProductCategoryRequestDTO productCategory,Long id)throws Exception;

    void deleteById(Long id)throws Exception;
}
