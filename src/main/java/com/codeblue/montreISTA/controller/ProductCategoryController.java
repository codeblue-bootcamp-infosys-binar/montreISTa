package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ProductCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductCategoryController {
    private ProductCategoryService productCategoryService;

    /**
     * findAll
     * @return
     */
    @GetMapping("/productCategory")
    public ResponseEntity<Object> findAllProductCategory(){
        try{
            List<ProductCategory> results = productCategoryService.findAll();
            return ResponseHandler.generateResponse("successfully retrieved product category", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * findByProductname
     * @param keyword
     * @return
     */
    @GetMapping("/productCategory/productname")
    public ResponseEntity<Object> findByProductname(@Param("keyword") String keyword){
        try{
            List<ProductCategory> results = productCategoryService.findByProductName(keyword);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * findBycategoryname
     * @param keyword
     * @return
     */
    @GetMapping("/productCategory/categoryname")
    public ResponseEntity<Object> findByCategoryName(@Param("keyword") String keyword){
        try{
            List<ProductCategory> results = productCategoryService.findByCategoryName(keyword);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * postProductCategory
     * @param productCategory
     * @return
     */
    @PostMapping("/productCategory")
    public ResponseEntity<Object> postProductCategory(@RequestBody ProductCategory productCategory) {
        try {
            ProductCategory results = productCategoryService.createProductCategory(productCategory);
            return ResponseHandler.generateResponse("successfully create product category", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * updateProduct
     * @param id
     * @param productCategory
     * @return
     */
    @PutMapping("/productCategory/{id}")
    public ResponseEntity<Object> updateProductCategory(@PathVariable Long id,@RequestBody ProductCategory productCategory) {
        try {
            ProductCategory results = productCategoryService.updateProductCategory(productCategory,id);
            return ResponseHandler.generateResponse("successfully update product category", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * delete
     * @param id
     * @return
     */
    @DeleteMapping("/productCategory/{id}")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long id) {
        try {
            productCategoryService.deleteById(id);
            return ResponseHandler.generateResponse("successfully delete product category", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
