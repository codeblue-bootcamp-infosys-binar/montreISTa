package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProduct();

    Optional<Product> findProductById(Long id);

    List<Product> findByProductName(String name);

    List<Product> findBySellerName(String name);

    List<Product> findByStoreName(String name);

    List<Product> findByCategoryId(Long id);

    List<Product> findProductBySellerId(Long id);
    Product createProduct(ProductRequestDTO productRequestDTO);

    Product updateProduct(Product product, Long id);

    void deleteProduct(Long id);
}
