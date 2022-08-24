package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ProductCategoryRequestDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryResponseDTO;
import com.codeblue.montreISTA.entity.ProductCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductCategoryService {
    ResponseEntity<Object> findAll();
    ResponseEntity<Object> findByProductName(String keyword)throws Exception;
    ResponseEntity<Object> findByProductId(Long id)throws Exception;
    ResponseEntity<Object> findByCategoryId(Long id)throws Exception;
    ResponseEntity<Object> findByCategoryName(String keyword)throws Exception;

    ResponseEntity<Object> createProductCategory(ProductCategoryRequestDTO productCategory) throws Exception;
    ResponseEntity<Object> updateProductCategory(ProductCategoryRequestDTO productCategory,Long id)throws Exception;

    ResponseEntity<Object> deleteById(Long id)throws Exception;
}
