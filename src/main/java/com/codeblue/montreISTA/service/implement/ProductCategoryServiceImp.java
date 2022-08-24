package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.controller.ProductCategoryController;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.repository.ProductCategoryRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ProductCategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImp implements ProductCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

    private static final String Line = "====================";
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseEntity<Object> findAll() {
        List<ProductCategory> photos = productCategoryRepository.findAll();
        List<ProductCategory> results = productCategoryRepository.findAll();
        try {
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Product Category     ====================");
            for (ProductCategory productcategoryData : photos) {
                Map<String, Object> productcategory = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Product Category ID     : " + productcategoryData.getProductCategoryId());
                logger.info("Category ID             : " + productcategoryData.getCategory());
                logger.info("Product  ID             : " + productcategoryData.getProduct());
                productcategory.put("Product Category ID   ", productcategoryData.getProductCategoryId());
                productcategory.put("Category ID           ", productcategoryData.getCategory());
                productcategory.put("Product  ID           ", productcategoryData.getProduct());
                maps.add(productcategory);
            }
            logger.info("==================== Logger End Get All Product Category   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully get all product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Product Category     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!")));
            logger.info("==================== Logger End Get All Product Category     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product category had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByProductName(String keyword) throws Exception {
        try {
            List<ProductCategory> results = productCategoryRepository.findByProductProductNameIgnoreCaseContaining(keyword);
            if (results.isEmpty()) {
                throw new Exception("Product Category not found");
            }
            logger.info(Line + "Logger Start Get productname " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get productname " + Line);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product category had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByProductId(Long id) throws Exception {
        try {
            List<ProductCategory> results = productCategoryRepository.findByProductId(id);
            if (results.isEmpty()) {
                throw new Exception("Product Category not found");
            }
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

    @Override
    public ResponseEntity<Object> findByCategoryId(Long id) throws Exception {
        try {
            List<ProductCategory> results = productCategoryRepository.findByCategoryCategoriesId(id);
            if (results.isEmpty()) {
                throw new Exception("Product Category not found");
            }
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

    @Override
    public ResponseEntity<Object> findByCategoryName(String keyword) throws Exception {
        try {
            List<ProductCategory> results = productCategoryRepository.findByCategoryNameIgnoreCaseContaining(keyword);
            if (results.isEmpty()) {
                throw new Exception("Product Category not found");
            }
            logger.info(Line + "Logger Start Get categoryname " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get categoryname " + Line);
            return ResponseHandler.generateResponse("successfully find product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "product category had no value!");
        }
    }


    @Override
    public ResponseEntity<Object> createProductCategory(ProductCategoryRequestDTO productCategory) throws Exception {
        try {
            List<ProductCategory> productCategories = productCategoryRepository.findByProductId(productCategory.getProduct_id());
            int count = productCategories.size();
            if (count >= 4) {
                throw new Exception("Product can only have 4 categories");
            }
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(productCategories));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create product category", HttpStatus.OK, productCategories);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create product category!");
        }
    }

    @Override
    public ResponseEntity<Object> updateProductCategory(ProductCategoryRequestDTO productCategory, Long id) throws Exception {
        try {
            Optional<ProductCategory> productCategoryId = productCategoryRepository.findById(id);
            if (productCategoryId.isEmpty()) {
                throw new Exception("Product Category not found");
            }
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(productCategoryId));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully update product category", HttpStatus.OK, productCategoryId);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update product category!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id) throws Exception {
        try {
            Optional<ProductCategory> productCategoryId = productCategoryRepository.findById(id);
            if (productCategoryId.isEmpty()) {
                throw new Exception("Product Category not found");
            }
            productCategoryRepository.deleteById(id);
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

    public List<ProductCategoryResponseDTO> convertDTO(List<ProductCategory> productCategories) {
        List<ProductCategoryResponseDTO> productCategoryResponseDTO = new ArrayList<>();

        for (ProductCategory productCategory : productCategories) {
            Optional<Product> productId = productRepository.findById(productCategory.getProduct().getId());
            Product product = productId.get();
            Optional<Category> categoryId = categoryRepository.findById(productCategory.getCategory().getCategoriesId());
            Category category = categoryId.get();
            ProductToProductCategoryDTO productDTO = product.convertToProductCategory();
            CategoryResponseDTO categoryDTO = category.convertToResponse();
            ProductCategoryResponseDTO responseDTO = productCategory.convertToResponse(productDTO, categoryDTO);
            productCategoryResponseDTO.add(responseDTO);
        }

        return productCategoryResponseDTO;
    }

    public ProductCategoryResponseDTO requestToEntity(ProductCategoryRequestDTO productCategory, Long id) throws Exception {
        Product product = productRepository.findById(productCategory.getProduct_id()).orElseThrow(() -> new Exception("Product not found"));
        Category category = categoryRepository.findById(productCategory.getCategory_id()).orElseThrow(() -> new Exception("Category not found"));
        //save to entity
        ProductCategory saveProductCategory = productCategory.convertToEntity(product, category);
        if (id != null) {
            saveProductCategory.setProductCategoryId(id);
        }
        productCategoryRepository.save(saveProductCategory);
        ProductToProductCategoryDTO productDTO = product.convertToProductCategory();
        CategoryResponseDTO categoryDTO = category.convertToResponse();

        return saveProductCategory.convertToResponse(productDTO, categoryDTO);
    }
}
