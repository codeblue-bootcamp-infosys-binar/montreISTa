package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.CategoryRequestDTO;
import com.codeblue.montreISTA.DTO.CategoryResponseDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.helper.ResourceNotFoundException;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll()throws ResourceNotFoundException{
        List<Category> categories =categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new ResourceNotFoundException("Category does not exist");
        }
        return categories;
    }

    @Override
    public List<Category> findByProductId(Long id){
        List<Category> categories = categoryRepository.findByProductsProductId(id);
        if (categories.isEmpty()){
            throw new ResourceNotFoundException("Category does not exist");
        }
        return categories;
    }

    @Override
    public Optional<Category> findById(Long id)throws ResourceNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw new ResourceNotFoundException("Category does not exist");
        }
        return optionalCategory;
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO)throws Exception {
        Category category = categoryRequestDTO.convertToEntity();
        Optional<Category> categoryCheck = categoryRepository.findByNameIgnoreCase(category.getName());
        if (categoryCheck.isPresent()){
            throw new Exception("Category has been exist");
        }
        return categoryRepository.save(category).convertToResponse();
    }

    @Override
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO,Long id)throws Exception {
        Category category = categoryRequestDTO.convertToEntity();
        categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category does not exist"));
        Optional<Category> categoryCheck = categoryRepository.findByNameIgnoreCase(category.getName());
        if (categoryCheck.isPresent()){
            throw new Exception("Category has been exist");
        }
        return categoryRepository.save(category).convertToResponse();
    }

    @Override
    public void deleteCategory(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw new RuntimeException("Category does not exist with id " + id);
        }
        categoryRepository.deleteById(id);
    }
}
