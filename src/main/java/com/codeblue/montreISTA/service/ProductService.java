package com.codeblue.montreISTA.service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<PhotoServiceImp.Product> findAllProduct();

    Optional<PhotoServiceImp.Product> findProductById(Long id);

    PhotoServiceImp.Product createProduct(PhotoServiceImp.Product product);

    PhotoServiceImp.Product updateProduct(PhotoServiceImp.Product product);

    void deleteProduct(Long id);
}
