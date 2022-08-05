package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProduct();

    Optional<Product> findProductById(Long id);

    List<Product> findByProductName(String name);

    List<Product> findBySellerName(String name);

    List<Product> findByStoreName(String name);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);
}
