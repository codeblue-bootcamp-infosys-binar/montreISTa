package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.CategoryRequestDTO;
import com.codeblue.montreISTA.DTO.CategoryResponseDTO;
import com.codeblue.montreISTA.controller.CategoryController;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.helper.ResourceNotFoundException;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private static final String Line = "====================";
    private final CategoryRepository categoryRepository;
    public ResponseEntity<Object> findAll() throws ResourceNotFoundException {
        try {
            List<Category> categories = categoryRepository.findAll();
            if (categories.isEmpty()) {
                throw new ResourceNotFoundException("Category does not exist");
            }
            List<CategoryResponseDTO> categoryResponseDTOS = new ArrayList<>();
            logger.info(Line + " Logger Start Get All " + Line);
            for (Category category : categories) {
                CategoryResponseDTO categoryDTO = category.convertToResponse();
                categoryResponseDTOS.add(categoryDTO);
                logger.info("CategoryId : " + category.getCategoriesId() + " Name : " + category.getName());
            }
            logger.info(Line + " Logger End Get All " + Line);
            return ResponseHandler.generateResponse("successfully retrieved category", HttpStatus.OK, categoryResponseDTOS);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "no data");
        }
    }

    @Override
    public ResponseEntity<Object> findByProductId(Long id) {
        try {
            List<Category> categories = categoryRepository.findByProductsProductId(id);
            if (categories.isEmpty()) {
                throw new ResourceNotFoundException("Category does not exist");
            }
            List<CategoryResponseDTO> categoryResponseDTO = new ArrayList<>();
            for (Category category : categories) {
                CategoryResponseDTO categoryDTO = category.convertToResponse();
                categoryResponseDTO.add(categoryDTO);
            }
            logger.info(Line + "Logger Start Get category Id " + Line);
            logger.info(String.valueOf(categoryResponseDTO));
            logger.info(Line + "Logger End Get category Id " + Line);
            return ResponseHandler.generateResponse("success get category by id", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Category had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findById(Long id) throws ResourceNotFoundException {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isEmpty()) {
                throw new ResourceNotFoundException("Category does not exist");
            }
            CategoryResponseDTO categoryResponseDTO = optionalCategory.get().convertToResponse();
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(categoryResponseDTO));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("success get id", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Category had no value");
        }
    }

    @Override
    public ResponseEntity<Object> createCategory(CategoryRequestDTO categoryRequestDTO) throws Exception {
        try {
            if(categoryRequestDTO.getName()==null){
                throw new Exception("Please check again your input, it can't empty");
            }
            Category category = categoryRequestDTO.convertToEntity();
            Optional<Category> categoryCheck = categoryRepository.findByNameIgnoreCase(category.getName());
            if (categoryCheck.isPresent()) {
                throw new Exception("Category has been exist");
            }
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(categoryCheck));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully created category", HttpStatus.OK, categoryCheck);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create Category!");
        }
    }

    @Override
    public ResponseEntity<Object> updateCategory(CategoryRequestDTO categoryRequestDTO, Long id) throws Exception {
        try {
            if(categoryRequestDTO.getName()==null){
                throw new Exception("Please check again your input, it can't empty");
            }
            Category category = categoryRequestDTO.convertToEntity();
            categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category does not exist"));
            Optional<Category> categoryCheck = categoryRepository.findByNameIgnoreCase(category.getName());
            if (categoryCheck.isPresent()) {
                throw new Exception("Category has been exist");
            }
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(categoryCheck));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated category", HttpStatus.OK, categoryCheck);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update category!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteCategory(Long id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isEmpty()) {
                throw new RuntimeException("Category does not exist with id " + id);
            }
            categoryRepository.deleteById(id);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("Success Delete", HttpStatus.OK, "success delete category");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Delete Category");
        }
    }
}
