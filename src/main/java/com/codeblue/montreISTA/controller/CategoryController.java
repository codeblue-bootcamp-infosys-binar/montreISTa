package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.CategoryRequestDTO;
import com.codeblue.montreISTA.DTO.CategoryResponseDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "13. Category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private static final String Line = "====================";
    @Autowired
    CategoryService categoryService;

    //GET ALL
    @GetMapping("/categories")
    public ResponseEntity<Object> getAllCategory(){
        try{
            List<Category> result = categoryService.findAll();
            List<CategoryResponseDTO> categoryResponseDTOS = new ArrayList<>();
            logger.info(Line + " Logger Start Get All " + Line);
            for(Category category: result){
                CategoryResponseDTO categoryDTO = category.convertToResponse();
                categoryResponseDTOS.add(categoryDTO);
                logger.info("CategoryId : " + category.getCategoriesId() + " Name : " + category.getName());
            }
            logger.info(Line + " Logger End Get All " + Line);
            return ResponseHandler.generateResponse("successfully retrieved category", HttpStatus.OK, categoryResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "no data");
        }
    }

    //GET BY ID
    @GetMapping("/categories/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable("id") Long id){
        try{
            Optional<Category> result = categoryService.findById(id);
            CategoryResponseDTO categoryResponseDTO = result.get().convertToResponse();
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(categoryResponseDTO));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("success get id", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Category had no value");
        }
    }

    //GET BY PRODUCT ID
    @GetMapping("/categories/product/{id}")
    public ResponseEntity<Object> getByProductId(@PathVariable("id") Long id){
        try{
            List<Category> categories = categoryService.findByProductId(id);
            List<CategoryResponseDTO> categoryResponseDTO = new ArrayList<>();
            for (Category category : categories){
                CategoryResponseDTO categoryDTO = category.convertToResponse();
                categoryResponseDTO.add(categoryDTO);
            }
            logger.info(Line + "Logger Start Get category Id " + Line);
            logger.info(String.valueOf(categoryResponseDTO));
            logger.info(Line + "Logger End Get category Id " + Line);
            return ResponseHandler.generateResponse("success get category by id", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e ){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Category had no value!");
        }
    }

    //CREATE
    @PostMapping("/dashboard/categories/create")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        try{
            Category category = categoryRequestDTO.convertToEntity();
            Category result = categoryService.createCategory(category);
            CategoryResponseDTO categoryResponseDTO = result.convertToResponse();
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(categoryResponseDTO));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully created category", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed create Category!");
        }
    }


    //UPDATE
    @PutMapping("/dashboard/categories/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO categoryRequestDTO){
        try{
            Optional<Category> targetCategory = categoryService.findById(id);
            Category updateCategory = targetCategory.get();
            updateCategory.setCategoriesId(id);
            updateCategory.setName(categoryRequestDTO.getName());
            categoryService.updateCategory(updateCategory);
            CategoryResponseDTO categoryResponseDTO = updateCategory.convertToResponse();
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(categoryResponseDTO));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated category", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed update category!");
        }
    }

    //DELETE
    @DeleteMapping("/dashboard/categories/delete/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id){
        try{
            categoryService.deleteCategory(id);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("Success Delete", HttpStatus.OK, null);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Delete Category");
        }
    }
}
