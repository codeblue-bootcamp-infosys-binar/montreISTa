package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryRequestDTO;
import com.codeblue.montreISTA.DTO.ProductCategoryResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.repository.ProductCategoryRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImp implements ProductCategoryService {
    private ProductCategoryRepository productCategoryRepository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    @Override
    public List<ProductCategoryResponseDTO> findAll() {
    List<ProductCategory> results= productCategoryRepository.findAll();
    return convertDTO(results);
    }

    @Override
    public List<ProductCategoryResponseDTO> findByProductName(String keyword) throws Exception{
        List<ProductCategory> results = productCategoryRepository.findByProductProductNameContaining(keyword);
        if(results==null){
            throw new Exception("Product Category not found");
        }
        return convertDTO(results);
    }

    @Override
    public List<ProductCategoryResponseDTO> findByCategoryName(String keyword) throws Exception {
        List<ProductCategory> results = productCategoryRepository.findByCategoryNameContaining(keyword);
        if(results==null){
            throw new Exception("Product Category not found");
        }
        return convertDTO(results);
    }


    @Override
    public ProductCategoryResponseDTO createProductCategory(ProductCategoryRequestDTO productCategory) {
        //call product and category
        Optional<Product> productId = productRepository.findById(productCategory.getProduct_id());
        Product product = productId.get();
        Optional<Category> categoryId = categoryRepository.findById(productCategory.getCategory_id());
        Category category= categoryId.get();

        //save to entity
        ProductCategory saveProductCategory = productCategory.convertToEntity(product,category);
        productCategoryRepository.save(saveProductCategory);

        //entity to response
        List<PhotoProductDTO> photosDTO = saveProductCategory.getProduct().getPhotos().stream()
                .map(Photo::convertToProduct)
                .collect(Collectors.toList());
        return saveProductCategory.convertToResponse(photosDTO);
    }

    @Override
    public ProductCategoryResponseDTO updateProductCategory(ProductCategoryRequestDTO productCategory,Long id) throws Exception{
        Optional<ProductCategory> productCategoryId = productCategoryRepository.findById(id);
        if(productCategoryId.isEmpty()){
            throw new Exception("Product Category not found");
        }
        Optional<Product> productId = productRepository.findById(productCategory.getProduct_id());
        Product product = productId.get();
        Optional<Category> categoryId = categoryRepository.findById(productCategory.getCategory_id());
        Category category= categoryId.get();

        //save to entity
        ProductCategory saveProductCategory = productCategory.convertToEntity(product,category);
        saveProductCategory.setProductCategoryId(id);
        productCategoryRepository.save(saveProductCategory);

        //entity to response
        List<PhotoProductDTO> photosDTO = saveProductCategory.getProduct().getPhotos().stream()
                .map(Photo::convertToProduct)
                .collect(Collectors.toList());
        return saveProductCategory.convertToResponse(photosDTO);
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
            List<PhotoProductDTO> photosDTO = productCategory.getProduct().getPhotos().stream()
                    .map(Photo::convertToProduct)
                    .collect(Collectors.toList());
            ProductCategoryResponseDTO responseDTO = productCategory.convertToResponse(photosDTO);
            productCategoryResponseDTO.add(responseDTO);
        }

        return productCategoryResponseDTO;
    }
}
