package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.ProductCategoryRequestDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryResponseDTO;
import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="05. Product Category")
public class ProductCategoryController {
    private ProductCategoryService productCategoryService;

    /**
     * findAll
     * @return
     */
    @GetMapping("/productCategories")
    public ResponseEntity<Object> findAllProductCategory(){
        try{
            List<ProductCategoryResponseDTO> results = productCategoryService.findAll();
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
            List<ProductCategoryResponseDTO> results = productCategoryService.findByProductName(keyword);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @GetMapping("/productCategory/product/{id}")
    public ResponseEntity<Object> findByProductId(@PathVariable Long id) {
        try {
            List<ProductCategoryResponseDTO> results = productCategoryService.findByProductId(id);
            return ResponseHandler.generateResponse("successfully delete product category", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
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
            List<ProductCategoryResponseDTO> results = productCategoryService.findByCategoryName(keyword);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @GetMapping("/productCategory/category/{id}")
    public ResponseEntity<Object> findByCategoryId(@PathVariable Long id) {
        try {
            List<ProductCategoryResponseDTO> results = productCategoryService.findByCategoryId(id);
            return ResponseHandler.generateResponse("successfully delete product category", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * post
     * @param productCategory
     * @return
     */
    @PostMapping("/user/productCategory")
    public ResponseEntity<Object> postProductCategory(@RequestBody ProductCategoryRequestDTO productCategory) throws Exception {
        try {
            ProductCategoryResponseDTO results = productCategoryService.createProductCategory(productCategory);
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
    @PutMapping("/user/productCategory/{id}")
    public ResponseEntity<Object> updateProductCategory(@PathVariable Long id,@RequestBody ProductCategoryRequestDTO productCategory) {
        try {
            ProductCategoryResponseDTO results = productCategoryService.updateProductCategory(productCategory,id);
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

    @DeleteMapping("/user/productCategory/{id}")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long id) {
        try {
            productCategoryService.deleteById(id);
            return ResponseHandler.generateResponse("successfully delete product category", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
