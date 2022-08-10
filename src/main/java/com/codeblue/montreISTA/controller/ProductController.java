package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.*;
import com.codeblue.montreISTA.service.implement.SellerServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Tag(name="3. Product")
public class ProductController {

    @Autowired
    ProductService productService;


    @Autowired
    CategoryService categoryService;

    @Autowired
    SellerServiceImpl sellerService;

    //GET ALL PRODUCTS
    @GetMapping("/products")
    public ResponseEntity<Object> getAllProduct(){
        try{
            List<Product> products = productService.findAllProduct();
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }


    //GET ALL PRODUCTS BY SELLER ID
    @GetMapping("/products/store/{seller_id}")
    public ResponseEntity<Object> getAllProductBySellerId(@PathVariable("seller_id") Long sellerId){
        try{
            List<Product> products = productService.findProductBySellerId(sellerId);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, null);
        }
    }

    //GET ONE PRODUCT BY ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id){
        try{
            Optional<Product> product = productService.findProductById(id);
            ProductResponseDTO productResponseDTO = DTOConverter.convertOneProducts(product.get());

            return ResponseHandler.generateResponse("successfully retrieved product", HttpStatus.OK, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    //GET BY PRODUCT NAME
    @GetMapping("/products/productname")
    public ResponseEntity<Object> getAllProductByProductName(@RequestParam String productName){
        try{
            List<Product> products = productService.findByProductName(productName);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }

    //GET BY SELLER USERNAME
    @GetMapping("/products/sellername")
    public ResponseEntity<Object> getAllProductBySellerName(@RequestParam String sellername){
        try{
            List<Product> products = productService.findBySellerName(sellername);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }

    //GET BY STORE NAME
    @GetMapping("/products/storename")
    public ResponseEntity<Object> getAllProductByStoreName(@RequestParam String storeName){
        try{
            List<Product> products = productService.findByStoreName(storeName);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }

    //GET BY CATEGORY ID
    @GetMapping("/products/category/{id}")
    public ResponseEntity<Object> getAllProductsByCategoryId(@PathVariable("id") Long id){
        try{
            List<Product> products = productService.findByCategoryId(id);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    //CREATE PRODUCT
    @PostMapping("/products/create")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        try {
            Product newProduct = productService.createProduct(productRequestDTO);
            ProductResponseDTO productResponseDTO = DTOConverter.convertOneProducts(newProduct);

            return ResponseHandler.generateResponse("successfully retrieved product", HttpStatus.CREATED, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }

    //UPDATE PRODUCT
    @PutMapping("/products/update/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductRequestDTO productRequestDTO, @PathVariable("id") Long id){
        try{
            Product updateProduct = productService.updateProduct(productRequestDTO, id);
            ProductResponseDTO productResponseDTO = DTOConverter.convertOneProducts(updateProduct);

            return ResponseHandler.generateResponse("successfully updated product", HttpStatus.CREATED, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    //DELETE PRODUCT
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id){
        try{
            productService.deleteProduct(id);

            return ResponseHandler.generateResponse("successfully deleted product", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }
}