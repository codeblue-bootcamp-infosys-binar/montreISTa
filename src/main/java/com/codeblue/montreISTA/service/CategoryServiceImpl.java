package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(category.getCategoriesId());
        if(optionalCategory.isEmpty()){
            return null;
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw new RuntimeException("Category does not exist with id " + id);
        }

        categoryRepository.deleteById(id);
    }
}
