package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.CategoryRequestDTO;
import com.codeblue.montreISTA.DTO.CategoryResponseDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    List<Category> findByProductId(Long id);

    Optional<Category> findById(Long id);

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO)throws Exception;

    CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO,Long id)throws Exception;

    void deleteCategory(Long id);
}
