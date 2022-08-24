package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController
@Tag(name = "03. Product")
@SecurityRequirement(name = "bearer-key")
public class ProductController {


    private final ProductService productService;

    //GET ALL PRODUCTS
    @GetMapping("/products")
    public ResponseEntity<Object> getAllProduct(@RequestParam(required = false) String sort,
                                                @RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) boolean descending) throws Exception {

        return productService.findAllProduct(page, sort, descending);
    }

    //GET ALL PRODUCTS BY SELLER ID
    @GetMapping("user/products/my-product")
    public ResponseEntity<Object> getProductBySellerId(Authentication authentication,
                                                       @RequestParam(required = false) String sort,
                                                       @RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) boolean descending) throws Exception {

        return productService.findProductBySellerId(authentication, page, sort, descending);
    }

    //GET ONE PRODUCT BY ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id) throws Exception {

        return productService.findProductById(id);
    }

    //GET BY PRODUCT NAME
    @GetMapping("/products/product-name")
    public ResponseEntity<Object> getProductByProductName(@RequestParam String productName,
                                                          @RequestParam(required = false) String sort,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) boolean descending) throws Exception {

        return productService.findByProductName(productName, page, sort, descending);
    }

    //GET BY SELLER USERNAME
    @GetMapping("/products/seller-name")
    public ResponseEntity<Object> getProductBySellerName(@RequestParam String sellername,
                                                         @RequestParam(required = false) String sort,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) boolean descending) {

        return productService.findBySellerName(sellername, page, sort, descending);

    }

    //GET BY STORE NAME
    @GetMapping("/products/store-name")
    public ResponseEntity<Object> getProductByStoreName(@RequestParam String storeName,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) boolean descending) {

        return productService.findByStoreName(storeName, page, sort, descending);
    }

    @GetMapping("/products/category-name")
    public ResponseEntity<Object> getProductByCategoryName(@RequestParam String categoryName,
                                                           @RequestParam(required = false) String sort,
                                                           @RequestParam(required = false) Integer page,
                                                           @RequestParam(required = false) boolean descending) {

        return productService.findByCategoryName(categoryName, page, sort, descending);

    }

    //GET BY CATEGORY ID
    @GetMapping("/products/category/{id}")
    public ResponseEntity<Object> getProductsByCategoryId(@PathVariable("id") Long id,
                                                          @RequestParam(required = false) String sort,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) boolean descending) {

        return productService.findByCategoryId(id, page, sort, descending);

    }

    //CREATE PRODUCT
    @PostMapping("/user/products/post-product")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequestDTO productRequestDTO, Authentication authentication) throws Exception {

        return productService.createProduct(productRequestDTO, authentication);
    }

    //UPDATE PRODUCT
    @PutMapping("/user/products/edit-product/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductRequestDTO productRequestDTO, @PathVariable("id") Long id, Authentication authentication) throws Exception {
        return productService.updateProduct(productRequestDTO, id, authentication);
    }

    //DELETE PRODUCT
    @DeleteMapping("/user/products/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id, Authentication authentication) throws Exception {

        return productService.deleteProduct(id, authentication);

    }
}