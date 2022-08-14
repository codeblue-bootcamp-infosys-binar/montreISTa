package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Product;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProduct();

    Optional<Product> findProductById(Long id);

    List<Product> findByProductName(String name);

    List<Product> findBySellerName(String name);

    List<Product> findByStoreName(String name);

    List<Product> findByCategoryId(Long id);

    List<Product> findProductBySellerId(Authentication authentication) throws Exception;

    Product createProduct(ProductRequestDTO productRequestDTO,Authentication authentication) throws Exception;

    Product updateProduct(ProductRequestDTO productRequestDTO, Long id, Authentication authentication)throws Exception;

    void deleteProduct(Long id,Authentication authentication) throws Exception;
}