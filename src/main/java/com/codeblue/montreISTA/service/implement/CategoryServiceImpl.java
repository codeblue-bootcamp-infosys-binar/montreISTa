package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.helper.ResourceNotFoundException;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.service.CategoryService;
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
    public List<Category> findByProductId(Long id) {
        return categoryRepository.findByProductsProductProductId(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw new ResourceNotFoundException("Category does not exist");
        }
        return optionalCategory;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(category.getCategoriesId());
        if(optionalCategory.isEmpty()){
            throw new ResourceNotFoundException("Category does not exist");
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
