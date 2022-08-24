package com.codeblue.montreISTA.helper;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DTOConverter {

    private CategoryRepository categoryRepository;

//    @Autowired
//    private DTOConverter(CategoryService categoryService) {
//        DTOConverter.categoryService = categoryService;
//    }

    public List<PhotoProductDTO> convertPhoto(List<Photo> photos){

        if(photos != null){
            List<PhotoProductDTO> photosDTO = new ArrayList<>();

            for(Photo photo : photos){
                PhotoProductDTO photoConvert = photo.convertToProduct();
                photosDTO.add(photoConvert);
            }

            return photosDTO;
        } else {
            return null;
        }
    }

    public List<String> convertCategories(List<Category> categories){

        if(categories != null){
            List<String> categoriesDTO = new ArrayList<>();

            for(Category category : categories){
                String categoryDTO = category.getName();
                categoriesDTO.add(categoryDTO);
            }

            return categoriesDTO;
        } else {
            return null;
        }
    }

    public List<ProductResponseDTO> convertProducts(List<Product> products){
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();

        for(Product product : products){

            List<PhotoProductDTO> photosDTO = convertPhoto(product.getPhotos());
            List<Category> categoryList = categoryRepository.findByProductsProductId(product.getId());
            List<String> categoriesDTO = convertCategories(categoryList);

            ProductResponseDTO productDTO = product.convertToResponse(photosDTO, categoriesDTO);
            productResponseDTOS.add(productDTO);

        }
        return productResponseDTOS;
    }

    public ProductResponseDTO convertOneProducts(Product product){

        List<PhotoProductDTO> photosDTO = convertPhoto(product.getPhotos());
        List<Category> categoryList = categoryRepository.findByProductsProductId(product.getId());
        List<String> categoriesDTO = convertCategories(categoryList);

        return product.convertToResponse(photosDTO, categoriesDTO);
    }

    public WishlistResponseDTO convertWishlist(Wishlist wishlist){
        Product product = wishlist.getProduct();
//                 products.add(product);
        ProductResponseDTO productDTO = this.convertOneProducts(product);

        return wishlist.convertToResponse(productDTO);

    }

    public LoginSellerResponseDTO convertLoginSeller(Seller seller,List<Product> products){
        SellerResponseDTO my_store = seller.convertToResponse();
        List<ProductResponseDTO> my_products = this.convertProducts(products);
        return seller.convertToLoginSeller(my_store,my_products);
    }
}