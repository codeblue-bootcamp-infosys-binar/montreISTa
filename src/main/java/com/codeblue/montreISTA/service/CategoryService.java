package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.CategoryRequestDTO;
import com.codeblue.montreISTA.DTO.CategoryResponseDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    ResponseEntity<Object> findAll();

    ResponseEntity<Object> findByProductId(Long id);

    ResponseEntity<Object> findById(Long id);

    ResponseEntity<Object> createCategory(CategoryRequestDTO categoryRequestDTO)throws Exception;

    ResponseEntity<Object> updateCategory(CategoryRequestDTO categoryRequestDTO,Long id)throws Exception;

    ResponseEntity<Object> deleteCategory(Long id);
}
