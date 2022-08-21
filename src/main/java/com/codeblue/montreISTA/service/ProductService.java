package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Product;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProduct(Integer page, String sort, boolean descending);

    Product findBySellerUsername(String keyword) throws Exception;

    Product findProductById(Long id)throws Exception;

    List<Product> findByProductName(String name, Integer page, String sort, boolean descending);

    List<Product> findBySellerName(String name, Integer page, String sort, boolean descending);

    List<Product> findByStoreName(String name, Integer page, String sort, boolean descending);

    List<Product> findByCategoryId(Long id, Integer page, String sort, boolean descending);
    
     List<Product> findByCategoryName(String name)throws Exception;

    List<Product> findByCategoryName(String name, Integer page, String sort, boolean descending);

    List<Product> findProductBySellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    Product createProduct(ProductRequestDTO productRequestDTO, Authentication authentication) throws Exception;

    Product updateProduct(ProductRequestDTO productRequestDTO, Long id, Authentication authentication)throws Exception;

    void deleteProduct(Long id,Authentication authentication) throws Exception;
}