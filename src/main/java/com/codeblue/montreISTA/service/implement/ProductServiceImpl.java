package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.SellerRepository;
import com.codeblue.montreISTA.service.ProductService;
import com.codeblue.montreISTA.service.implement.SellerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByProductName(String name) {
        return productRepository.findByProductNameIgnoreCaseContaining(name);
    }

    @Override
    public List<Product> findBySellerName(String name) {
        return productRepository.findBySellerUserIdNameIgnoreCaseContaining(name);
    }

    @Override
    public List<Product> findByStoreName(String name) {
        return productRepository.findBySellerStoreNameIgnoreCaseContaining(name);
    }

    @Override
    public List<Product> findByCategoryId(Long id) {
        return productRepository.findByCategoriesCategoryCategoriesId(id);
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
        int count = products.size();
        if(count >=4 ){
            throw new Exception("User can only have 4 Products");
        }
        Seller seller = sellerRepository.findById(productRequestDTO.getSellerId()).orElseThrow(()->new Exception("Seller not found"));
        Product newProduct = productRequestDTO.convertToEntity(seller);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(ProductRequestDTO productRequestDTO, Long id) throws Exception{

        //GET SELLER FROM DATABASE BY ID
        Seller productSeller = sellerRepository.findById(productRequestDTO.getSellerId()).orElseThrow(()->new Exception("Seller not found"));
        Product product = productRequestDTO.convertToEntity(productSeller);

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
