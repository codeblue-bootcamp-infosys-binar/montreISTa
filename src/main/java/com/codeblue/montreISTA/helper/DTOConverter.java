package com.codeblue.montreISTA.helper;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOConverter {

    private static CategoryService categoryService;

    @Autowired
    private DTOConverter(CategoryService categoryService) {
        DTOConverter.categoryService = categoryService;
    }

    public static List<PhotoProductDTO> convertPhoto(List<Photo> photos){

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

    public static List<String> convertCategories(List<Category> categories){

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

    public static List<ProductResponseDTO> convertProducts(List<Product> products){
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();

        for(Product product : products){

            List<PhotoProductDTO> photosDTO = convertPhoto(product.getPhotos());
            List<Category> categoryList = categoryService.findByProductId(product.getId());
            List<String> categoriesDTO = convertCategories(categoryList);

            ProductResponseDTO productDTO = product.convertToResponse(photosDTO, categoriesDTO);
            productResponseDTOS.add(productDTO);

        }
        return productResponseDTOS;
    }

    public static ProductResponseDTO convertOneProducts(Product product){

        List<PhotoProductDTO> photosDTO = convertPhoto(product.getPhotos());
        List<Category> categoryList = categoryService.findByProductId(product.getId());
        List<String> categoriesDTO = convertCategories(categoryList);

        return product.convertToResponse(photosDTO, categoriesDTO);
    }

    public static WishlistResponseDTO convertWishlist(Wishlist wishlist){
        Product product = wishlist.getProduct();
//                 products.add(product);
        ProductResponseDTO productDTO = DTOConverter.convertOneProducts(product);

        return wishlist.convertToResponse(productDTO);

    }

    public static LoginSellerResponseDTO convertLoginSeller(Seller seller,List<Product> products){
        SellerResponseDTO my_store = seller.convertToResponse();
        List<ProductResponseDTO> my_products = DTOConverter.convertProducts(products);
        return seller.convertToLoginSeller(my_store,my_products);
    }
}