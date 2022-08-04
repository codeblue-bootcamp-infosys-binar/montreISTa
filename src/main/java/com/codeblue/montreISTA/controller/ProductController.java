package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.SellerRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @Autowired
    SellerRepository sellerRepository;
    //GET ALL
    @GetMapping("/products")
    public ResponseEntity<Object> getAllProduct(){
        try{
            List<Product> products = productServiceImpl.findAllProduct();

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, products);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ALL BY SELLER ID
    @GetMapping("/products/store/{seller_id}")
    public ResponseEntity<Object> getAllProductBySellerId(@PathVariable("seller_id") Long sellerId){
        try{
            List<Product> product = productServiceImpl.findProductBySellerId(sellerId);
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, product);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id){
        try{
            Optional<Product> product = productServiceImpl.findProductById(id);
            ProductResponseDTO productResponseDTO = product.get().convertToResponse();
            return ResponseHandler.generateResponse("successfully retrieved product", HttpStatus.OK, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/products/create")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        try {
            Optional<Seller> productSeller = sellerRepository.findById(productRequestDTO.getSellerId());
            Seller seller = productSeller.get();
            Product newProduct = productRequestDTO.convertToEntity(seller);
            productServiceImpl.createProduct(newProduct);
            ProductResponseDTO productResponseDTO = newProduct.convertToResponse();

            return ResponseHandler.generateResponse("successfully retrieved product", HttpStatus.CREATED, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/products/update/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable("id") Long id){
        try{
            Optional<Product> targetProduct = productServiceImpl.findProductById(id);
            Product updateProduct = targetProduct.get();
            updateProduct.setProductId(id);
            updateProduct.setProductName(product.getProductName());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setPrice(product.getPrice());

            productServiceImpl.updateProduct(updateProduct);
            return ResponseHandler.generateResponse("successfully updated product", HttpStatus.CREATED, updateProduct);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id){
        try{
            productServiceImpl.deleteProduct(id);
            return ResponseHandler.generateResponse("successfully deleted product", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }
}