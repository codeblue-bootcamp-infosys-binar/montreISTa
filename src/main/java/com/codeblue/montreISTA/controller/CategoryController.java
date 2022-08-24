package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.CategoryRequestDTO;
import com.codeblue.montreISTA.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Tag(name = "13. Category")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {


    @Autowired
    CategoryService categoryService;

    //GET ALL
    @GetMapping("/categories")
    public ResponseEntity<Object> getAllCategory(){
            return categoryService.findAll();
    }

    //GET BY ID
    @GetMapping("/categories/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable("id") Long id){
            return categoryService.findById(id);
    }

    //GET BY PRODUCT ID
    @GetMapping("/categories/product/{id}")
    public ResponseEntity<Object> getByProductId(@PathVariable("id") Long id){
            return categoryService.findByProductId(id);
    }

    //CREATE
    @PostMapping("/dashboard/categories/post")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) throws Exception {
            return categoryService.createCategory(categoryRequestDTO);
    }


    //UPDATE
    @PutMapping("/dashboard/categories/edit/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) throws Exception {
           return categoryService.updateCategory(categoryRequestDTO,id);
    }

    //DELETE
    @DeleteMapping("/dashboard/categories/delete/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id){
           return categoryService.deleteCategory(id);


    }
}
