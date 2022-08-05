package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    public List<Product> findAllProduct() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByProductName(String name) {
        List<Product> products = productRepository.findByProductNameIgnoreCaseContaining(name);
        return products;
    }

    @Override
    public List<Product> findBySellerName(String name) {
        List<Product> products = productRepository.findBySellerUserIdNameIgnoreCaseContaining(name);
        return products;
    }

    @Override
    public List<Product> findByStoreName(String name) {
        List<Product> products = productRepository.findBySellerStoreNameIgnoreCaseContaining(name);
        return products;
    }

    public List<Product> findProductBySellerId(Long id) {
        List<Product> product = productRepository.findBySellerSellerId(id);
        if(product.isEmpty()){
            return null;
        } else {
            return product;
        }
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product updateProduct) {
        return productRepository.save(updateProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
