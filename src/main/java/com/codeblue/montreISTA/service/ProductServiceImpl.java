package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerServiceImpl sellerServiceImpl;

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

    @Override
    public List<Product> findByCategoryId(Long id) {
        List<Product> products = productRepository.findByCategoriesCategoryCategoriesId(id);
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

    public Product createProduct(ProductRequestDTO productRequestDTO) {
        Optional<Seller> productSeller = sellerServiceImpl.findSellerById(productRequestDTO.getSellerId());
        Seller seller = productSeller.get();
        Product newProduct = productRequestDTO.convertToEntity(seller);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(Product product, Long id) {

        Optional<Product> targetProduct = findProductById(id);
        Product updateProduct = targetProduct.get();

        //UPDATING PRODUCT DATA
        updateProduct.setProductId(id);
        updateProduct.setSeller(product.getSeller());
        updateProduct.setProductName(product.getProductName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());

        //SAVING THE UPDATES TO DATABASE
        return productRepository.save(updateProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
