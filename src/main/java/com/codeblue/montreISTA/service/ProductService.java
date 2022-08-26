package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ProductService {

    ResponseEntity<Object> findAllProduct(Integer page, String sort, boolean descending)throws Exception;

    Product findBySellerUsername(String keyword) throws Exception;

    ResponseEntity<Object> findProductById(Long id)throws Exception;

    ResponseEntity<Object> findByProductName(String name, Integer page, String sort, boolean descending)throws Exception;

    ResponseEntity<Object> findBySellerName(String name, Integer page, String sort, boolean descending);

    ResponseEntity<Object> findByStoreName(String name, Integer page, String sort, boolean descending);

    ResponseEntity<Object> findByCategoryId(Long id, Integer page, String sort, boolean descending);

    ResponseEntity<Object> findByCategoryName(String name, Integer page, String sort, boolean descending);

    ResponseEntity<Object> findProductBySellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    ResponseEntity<Object> search(String keyword,Integer page, String sort, boolean descending) throws Exception;

    ResponseEntity<Object> createProduct(ProductRequestDTO productRequestDTO, Authentication authentication) throws Exception;

    ResponseEntity<Object> updateProduct(ProductRequestDTO productRequestDTO, Long id, Authentication authentication)throws Exception;

    ResponseEntity<Object> deleteProduct(Long id,Authentication authentication) throws Exception;
}