package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.repository.ProductCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImp implements ProductCategoryService {
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public List<ProductCategory> findAll() {

        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByProductName(String keyword) throws Exception{
        List<ProductCategory> results = productCategoryRepository.findByProductProductNameContaining(keyword);
        if(results==null){
            throw new Exception("Product Category not found");
        }
        return results;
    }

    @Override
    public List<ProductCategory> findByCategoryName(String keyword) throws Exception {
        List<ProductCategory> results = productCategoryRepository.findByCategoryNameContaining(keyword);
        if(results==null){
            throw new Exception("Product Category not found");
        }
        return results;
    }


    @Override
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(ProductCategory productCategory,Long id) throws Exception{
        Optional<ProductCategory> productCategoryId = productCategoryRepository.findById(id);
        if(productCategoryId.isEmpty()){
            throw new Exception("Product Category not found");
        }
        productCategory.setProductCategoryId(id);
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Optional<ProductCategory> productCategoryId = productCategoryRepository.findById(id);
        if(productCategoryId.isEmpty()){
            throw new Exception("Product Category not found");
        }
        productCategoryRepository.deleteById(id);
    }
}
