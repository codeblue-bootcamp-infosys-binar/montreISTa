package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.CategoryRequestDTO;
import com.codeblue.montreISTA.DTO.CategoryResponseDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //GET ALL
    @GetMapping("/categories")
    public ResponseEntity<Object> getAllCategory(){
        try{
            List<Category> result = categoryService.findAll();
            List<CategoryResponseDTO> categoryResponseDTOS = new ArrayList<>();

            for(Category category: result){
                CategoryResponseDTO categoryDTO = category.convertToResponse();
                categoryResponseDTOS.add(categoryDTO);
            }

            return ResponseHandler.generateResponse("successfully retrieved category", HttpStatus.OK, categoryResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //GET BY ID
    @GetMapping("/categories/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable("id") Long id){
        try{
            Optional<Category> result = categoryService.findById(id);
            CategoryResponseDTO categoryResponseDTO = result.get().convertToResponse();

            return ResponseHandler.generateResponse("successfully retrieved category", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }


    //CREATE
    @PostMapping("/categories/create")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        try{
            Category category = categoryRequestDTO.convertToEntity();
            Category result = categoryService.createCategory(category);
            CategoryResponseDTO categoryResponseDTO = result.convertToResponse();

            return ResponseHandler.generateResponse("successfully created category", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }


    //UPDATE
    @PutMapping("/categories/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO categoryRequestDTO){
        try{
            Optional<Category> targetCategory = categoryService.findById(id);
            Category updateCategory = targetCategory.get();
            updateCategory.setCategoriesId(id);
            updateCategory.setName(categoryRequestDTO.getName());

            categoryService.updateCategory(updateCategory);

            CategoryResponseDTO categoryResponseDTO = updateCategory.convertToResponse();

            return ResponseHandler.generateResponse("successfully updated category", HttpStatus.OK, categoryResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }

    //DELETE
    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseHandler.generateResponse("successfully deleted category", HttpStatus.OK, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }
}
