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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    public List<Product> findAllProduct() {
        return productRepository.findAllByOrderByCreatedAtAsc();
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

    @Override
    public List<Product> findProductBySellerId(Authentication authentication) throws Exception{
        Seller seller = sellerRepository.findByUserIdUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as seller"));
        List<Product> product = productRepository.findBySellerSellerId(seller.getSellerId());
        if(product.isEmpty()){
            throw new Exception("You don't have a product");
        }
        return product;
    }

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO,Authentication authentication) throws Exception{
        Seller seller = sellerRepository.findByUserIdUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));
        List<Product> products = productRepository.findBySellerSellerId(seller.getSellerId());
        int count = products.size();
        if(count >=4 ){
            throw new Exception("User can only have 4 Products");
        }
        Product newProduct = productRequestDTO.convertToEntity(seller);

        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(ProductRequestDTO productRequestDTO, Long id, Authentication authentication) throws Exception{
        Seller seller = sellerRepository.findByUserIdUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));
        //GET SELLER FROM DATABASE BY ID
        Product product = productRequestDTO.convertToEntity(seller);
        Product updateProduct = findProductById(id).orElseThrow(()->new Exception("Product Not Found"));
        if(seller!=updateProduct.getSeller()){
            throw new Exception("You only can edit your product");
        }
        //UPDATING PRODUCT DATA
        updateProduct.setProductId(id);
        updateProduct.setProductName(product.getProductName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());

        //SAVING THE UPDATES TO DATABASE
        return productRepository.save(updateProduct);
    }
    @Override
    public void deleteProduct(Long id, Authentication authentication)throws Exception {
        Seller seller = sellerRepository.findByUserIdUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));
        Product productById = findProductById(id).orElseThrow(()->new Exception("Product Not Found"));
        if(seller!=productById.getSeller()){
            throw new Exception("You only can delete your product");
        }
        productRepository.deleteById(id);
    }


}
