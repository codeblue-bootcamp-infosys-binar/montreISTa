package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}
