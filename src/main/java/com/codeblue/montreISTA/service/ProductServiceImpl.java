package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    public List<PhotoServiceImp.Product> findAllProduct() {
        List<PhotoServiceImp.Product> products = productRepository.findAll();
        return products;
    }

    public Optional<PhotoServiceImp.Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<PhotoServiceImp.Product> findProductBySellerId(Long id) {
        List<PhotoServiceImp.Product> product = productRepository.findBySellerId(id);
        if(product.isEmpty()){
            return null;
        } else {
            return product;
        }
    }

    public PhotoServiceImp.Product createProduct(PhotoServiceImp.Product product) {
        return productRepository.save(product);
    }

    public PhotoServiceImp.Product updateProduct(PhotoServiceImp.Product updateProduct) {
        return productRepository.save(updateProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
