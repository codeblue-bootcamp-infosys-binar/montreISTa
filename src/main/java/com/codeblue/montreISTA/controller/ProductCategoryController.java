package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.ProductCategoryRequestDTO;
import com.codeblue.montreISTA.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Tag(name="10. Product Category")
@SecurityRequirement(name = "bearer-key")
public class ProductCategoryController {




    private ProductCategoryService productCategoryService;

    /**
     * findAll
     * @return
     */
    @GetMapping("/product-categories")
    public ResponseEntity<Object> findAllProductCategory(){
          return productCategoryService.findAll();
    }

    @GetMapping("/product-category/product-name")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword) throws Exception {
            return productCategoryService.findByProductName(keyword);
    }
    @GetMapping("/product-category/product/{id}")
    public ResponseEntity<Object> findByProductId(@PathVariable Long id) throws Exception {
            return productCategoryService.findByProductId(id);
    }

    @GetMapping("/product-category/category-name")
    public ResponseEntity<Object> findByCategoryName(@Param("keyword") String keyword) throws Exception {
            return productCategoryService.findByCategoryName(keyword);
    }
    @GetMapping("/product-category/category/{id}")
    public ResponseEntity<Object> findByCategoryId(@PathVariable Long id) throws Exception {
            return productCategoryService.findByCategoryId(id);
    }

    @PostMapping("/dashboard/product-category")
    public ResponseEntity<Object> postProductCategory(@RequestBody ProductCategoryRequestDTO productCategory) throws Exception {
            return productCategoryService.createProductCategory(productCategory);
    }

    @PutMapping("/dashboard/product-category/{id}")
    public ResponseEntity<Object> updateProductCategory(@PathVariable Long id,@RequestBody ProductCategoryRequestDTO productCategory) throws Exception {
           return productCategoryService.updateProductCategory(productCategory,id);
    }

    @DeleteMapping("/dashboard/product-category/{id}")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long id) throws Exception {
          return  productCategoryService.deleteById(id);
    }
}
