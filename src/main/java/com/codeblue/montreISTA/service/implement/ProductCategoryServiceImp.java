package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.repository.ProductCategoryRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.service.ProductCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImp implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public List<ProductCategoryResponseDTO> findAll() {
    List<ProductCategory> results= productCategoryRepository.findAll();
    return convertDTO(results);
    }

    @Override
    public List<ProductCategoryResponseDTO> findByProductName(String keyword) throws Exception{
        List<ProductCategory> results = productCategoryRepository.findByProductProductNameIgnoreCaseContaining(keyword);
        if(results.isEmpty()){
            throw new Exception("Product Category not found");
        }
        return this.convertDTO(results);
    }

    @Override
    public List<ProductCategoryResponseDTO> findByProductId(Long id) throws Exception {
        List<ProductCategory> results = productCategoryRepository.findByProductId(id);
        if(results.isEmpty()){
            throw new Exception("Product Category not found");
        }
        return this.convertDTO(results);
    }

    @Override
    public List<ProductCategoryResponseDTO> findByCategoryId(Long id) throws Exception {
        List<ProductCategory> results = productCategoryRepository.findByCategoryCategoriesId(id);
        if(results.isEmpty()){
            throw new Exception("Product Category not found");
        }
        return this.convertDTO(results);
    }

    @Override
    public List<ProductCategoryResponseDTO> findByCategoryName(String keyword) throws Exception {
        List<ProductCategory> results = productCategoryRepository.findByCategoryNameIgnoreCaseContaining(keyword);
        if(results.isEmpty()){
            throw new Exception("Product Category not found");
        }
        return this.convertDTO(results);
    }


    @Override
    public ProductCategoryResponseDTO createProductCategory(ProductCategoryRequestDTO productCategory) throws Exception {
        List<ProductCategory> productCategories = productCategoryRepository.findByProductId(productCategory.getProduct_id());
        int count = productCategories.size();
        if(count>=4){
            throw new Exception("Product can only have 4 categories");
        }
        return this.requestToEntity(productCategory,null);
    }

    @Override
    public ProductCategoryResponseDTO updateProductCategory(ProductCategoryRequestDTO productCategory,Long id) throws Exception{
        Optional<ProductCategory> productCategoryId = productCategoryRepository.findById(id);
        if(productCategoryId.isEmpty()){
            throw new Exception("Product Category not found");
        }
        return this.requestToEntity(productCategory,id);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Optional<ProductCategory> productCategoryId = productCategoryRepository.findById(id);
        if(productCategoryId.isEmpty()){
            throw new Exception("Product Category not found");
        }
        productCategoryRepository.deleteById(id);
    }

    public List<ProductCategoryResponseDTO> convertDTO(List<ProductCategory> productCategories){
        List<ProductCategoryResponseDTO> productCategoryResponseDTO = new ArrayList<>();

        for(ProductCategory productCategory : productCategories) {
            Optional<Product> productId = productRepository.findById(productCategory.getProduct().getId());
            Product product = productId.get();
            Optional<Category> categoryId = categoryRepository.findById(productCategory.getCategory().getCategoriesId());
            Category category= categoryId.get();
            ProductToProductCategoryDTO productDTO = product.convertToProductCategory();
            CategoryResponseDTO categoryDTO = category.convertToResponse();
            ProductCategoryResponseDTO responseDTO = productCategory.convertToResponse(productDTO,categoryDTO);
            productCategoryResponseDTO.add(responseDTO);
        }

        return productCategoryResponseDTO;
    }
    public ProductCategoryResponseDTO requestToEntity (ProductCategoryRequestDTO productCategory, Long id)throws Exception{
        Product product = productRepository.findById(productCategory.getProduct_id()).orElseThrow(()-> new Exception("Product not found"));
        Category category= categoryRepository.findById(productCategory.getCategory_id()).orElseThrow(()-> new Exception("Category not found"));
        //save to entity
        ProductCategory saveProductCategory = productCategory.convertToEntity(product,category);
        if(id != null){
            saveProductCategory.setProductCategoryId(id);
        }
        productCategoryRepository.save(saveProductCategory);
        ProductToProductCategoryDTO productDTO = product.convertToProductCategory();
        CategoryResponseDTO categoryDTO = category.convertToResponse();

        return saveProductCategory.convertToResponse(productDTO,categoryDTO);
    }
}
