package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.*;
import com.codeblue.montreISTA.service.implement.SellerServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="03. Product")
@SecurityRequirement(name = "bearer-key")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private static final String Line = "====================";

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SellerServiceImpl sellerService;

    //GET ALL PRODUCTS
    @GetMapping("/products")
    public ResponseEntity<Object> getAllProduct(){
        try{
            List<Product> products = productService.findAllProduct();
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);
            logger.info("==================== Logger Start Get All Products     ====================");
            for(Product productData : products){
                Map<String, Object> product = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Product ID    : " + productData.getProductId());
                logger.info("Description   : " + productData.getDescription());
                logger.info("Price         : " + productData.getPrice());
                logger.info("Product name  : " + productData.getProductName());
                logger.info("Seller ID     : " + productData.getSeller());
            }
            logger.info("==================== Logger End Get All Products    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully get all products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Products     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Product had no value!")));
            logger.info("==================== Logger End Get All Products     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }


    //GET ALL PRODUCTS BY SELLER ID
    @GetMapping("user/products/seller")
    public ResponseEntity<Object> getAllProductBySellerId(Authentication authentication){
        try{
            List<Product> products = productService.findProductBySellerId(authentication);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);
            logger.info(Line + "Logger Start Get seller id " + Line);
            logger.info(String.valueOf(productResponseDTOS));
            logger.info(Line + "Logger End Get seller id " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    //GET ONE PRODUCT BY ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id){
        try{
            Optional<Product> product = productService.findProductById(id);
            ProductResponseDTO productResponseDTO = DTOConverter.convertOneProducts(product.get());
            logger.info(Line + "Logger Start Get product id " + Line);
            logger.info(String.valueOf(productResponseDTO));
            logger.info(Line + "Logger End Get product id " + Line);
            return ResponseHandler.generateResponse("successfully get product", HttpStatus.OK, productResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    //GET BY PRODUCT NAME
    @GetMapping("/products/name")
    public ResponseEntity<Object> getAllProductByProductName(@RequestParam String productName){
        try{
            List<Product> products = productService.findByProductName(productName);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);
            logger.info(Line + "Logger Start Get productname " + Line);
            logger.info(String.valueOf(productResponseDTOS));
            logger.info(Line + "Logger End Get productname " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Product had no value!");
        }
    }

    //GET BY SELLER USERNAME
    @GetMapping("/products/seller")
    public ResponseEntity<Object> getAllProductBySellerName(@RequestParam String sellername){
        try{
            List<Product> products = productService.findBySellerName(sellername);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);
            logger.info(Line + "Logger Start Get sellername " + Line);
            logger.info(String.valueOf(productResponseDTOS));
            logger.info(Line + "Logger End Get sellername " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Product had no value!");
        }
    }

    //GET BY STORE NAME
    @GetMapping("/products/store")
    public ResponseEntity<Object> getAllProductByStoreName(@RequestParam String storeName){
        try{
            List<Product> products = productService.findByStoreName(storeName);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);
            logger.info(Line + "Logger Start Get storename " + Line);
            logger.info(String.valueOf(productResponseDTOS));
            logger.info(Line + "Logger End Get storename " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Product had no value!");
        }
    }

    //GET BY CATEGORY ID
    @GetMapping("/products/category/{id}")
    public ResponseEntity<Object> getAllProductsByCategoryId(@PathVariable("id") Long id){
        try{
            List<Product> products = productService.findByCategoryId(id);
            List<ProductResponseDTO> productResponseDTOS = DTOConverter.convertProducts(products);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(productResponseDTOS));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    //CREATE PRODUCT
    @PostMapping("/user/products/create")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequestDTO productRequestDTO, Authentication authentication){
        try {
            Product newProduct = productService.createProduct(productRequestDTO,authentication);
            ProductResponseDTO productResponseDTO = DTOConverter.convertOneProducts(newProduct);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(productResponseDTO));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create product", HttpStatus.CREATED, productResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed create product!");
        }
    }

    //UPDATE PRODUCT
    @PutMapping("/user/products/update/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductRequestDTO productRequestDTO, @PathVariable("id") Long id,Authentication authentication){
        try{
            Product updateProduct = productService.updateProduct(productRequestDTO, id,authentication);
            ProductResponseDTO productResponseDTO = DTOConverter.convertOneProducts(updateProduct);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(productResponseDTO));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated product", HttpStatus.CREATED, productResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update product!");
        }
    }

    //DELETE PRODUCT
    @DeleteMapping("/user/products/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id,Authentication authentication){
        try{
            productService.deleteProduct(id,authentication);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted product", HttpStatus.OK, null);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete product!");
        }

    }
}