package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryRequestDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name="05. Product Category")
public class ProductCategoryController {



    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

    private static final String Line = "====================";
    private ProductCategoryService productCategoryService;

    /**
     * findAll
     * @return
     */
    @GetMapping("/productCategories")
    public ResponseEntity<Object> findAllProductCategory(){
        try{
            List<ProductCategoryResponseDTO> photos = productCategoryService.findAll();
            List<ProductCategoryResponseDTO> results = productCategoryService.findAll();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Product Category     ====================");
            for(ProductCategoryResponseDTO productcategoryData : photos){
                Map<String, Object> productcategory = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Product Category ID     : " + productcategoryData.getProduct_category_id());
                logger.info("Category ID             : " + productcategoryData.getCategory());
                logger.info("Product  ID             : " + productcategoryData.getProduct());
                productcategory.put("Product Category ID    ", productcategoryData.getProduct_category_id());
                productcategory.put("Category ID           ", productcategoryData.getCategory());
                productcategory.put("Product  ID           ", productcategoryData.getProduct());
                maps.add(productcategory);
            }
            logger.info("==================== Logger End Get All Product Category   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully get all product category", HttpStatus.OK, results);
        }catch (Exception e){
            logger.info("==================== Logger Start Get All Product Category     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Product had no value!")));
            logger.info("==================== Logger End Get All Product Category     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product category had no value!");
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
            logger.info(Line + "Logger Start Get productname " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get productname " + Line);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product category had no value!");
        }
    }
    @GetMapping("/productCategory/product/{id}")
    public ResponseEntity<Object> findByProductId(@PathVariable Long id) {
        try {
            List<ProductCategoryResponseDTO> results = productCategoryService.findByProductId(id);
            logger.info(Line + "Logger Start Get product id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get product id " + Line);
            return ResponseHandler.generateResponse("successfully delete product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Product category had no value!");
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
            logger.info(Line + "Logger Start Get categoryname " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get categoryname " + Line);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "product category had no value!");
        }
    }
    @GetMapping("/productCategory/category/{id}")
    public ResponseEntity<Object> findByCategoryId(@PathVariable Long id) {
        try {
            List<ProductCategoryResponseDTO> results = productCategoryService.findByCategoryId(id);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully delete product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Product category had no value!");
        }
    }

    /**
     * post
     * @param productCategory
     * @return
     */
    @PostMapping("/productCategory")
    public ResponseEntity<Object> postProductCategory(@RequestBody ProductCategoryRequestDTO productCategory) throws Exception{
        try {
            ProductCategoryResponseDTO results = productCategoryService.createProductCategory(productCategory);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create product category!");
        }
    }

    /**
     * updateProduct
     * @param id
     * @param productCategory
     * @return
     */
    @PutMapping("/productCategory/{id}")
    public ResponseEntity<Object> updateProductCategory(@PathVariable Long id,@RequestBody ProductCategoryRequestDTO productCategory) {
        try {
            ProductCategoryResponseDTO results = productCategoryService.updateProductCategory(productCategory,id);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully update product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update product category!");
        }
    }

    /**
     * delete
     * @param id
     * @return
     */
    @DeleteMapping("/productCategory/delete/{id}")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long id) {
        try {
            productCategoryService.deleteById(id);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully delete product category", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete product category!");
        }
    }
}
