package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.service.ProductService;
import com.codeblue.montreISTA.service.implement.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerServiceImpl sellerService;

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

    public Product createProduct(ProductRequestDTO productRequestDTO) throws Exception{
        List<Product> products = productRepository.findBySellerSellerId(productRequestDTO.getSellerId());
        Integer count = products.size();
        if(count >=4 ){
            throw new Exception("User can only have 4 Products");
        }
        Optional<Seller> productSeller = sellerService.findSellerById(productRequestDTO.getSellerId());
        Seller seller = productSeller.get();
        Product newProduct = productRequestDTO.convertToEntity(seller);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(ProductRequestDTO productRequestDTO, Long id) {

        //GET SELLER FROM DATABASE BY ID
        Optional<Seller> productSeller = sellerService.findSellerById(productRequestDTO.getSellerId());
        Product product = productRequestDTO.convertToEntity(productSeller.get());

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
