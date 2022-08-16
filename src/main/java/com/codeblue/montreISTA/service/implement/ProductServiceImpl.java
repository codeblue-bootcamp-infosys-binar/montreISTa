package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.CategoryRepository;
import com.codeblue.montreISTA.repository.ProductCategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<Product> findAllProduct() {
        return productRepository.findAllByOrderByCreatedAtAsc();
    }

    @Override
    public Product findBySellerUsername(String keyword) throws Exception{
        return productRepository.findFirstBySellerUserIdUsernameOrderByProductIdDesc(keyword)
                .orElseThrow(()->new Exception("Product not found"));
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
        if(productRequestDTO.getCategory().size()>=4){
            throw new Exception("Product can only have 4 category");
        }
        if(products.size() >=4 ){
            throw new Exception("User can only have 4 Products");
        }
        List<String> categories = productRequestDTO.getCategory();
        if(categories.isEmpty()){
            throw new Exception("Product must have 1 category");
        }
        //check categories
        this.checkCategories(categories);

        Product newProduct = productRequestDTO.convertToEntity(seller);
        Product saveProduct = productRepository.save(newProduct);

        this.addCategory(categories,saveProduct);

        return saveProduct;
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

        List<String> categories = productRequestDTO.getCategory();
        if(categories.isEmpty()){
            throw new Exception("Product must have 1 category");
        }
        List<Category> categoryProduct = categoryRepository.findByProductsProductProductId(id);
        if(categories.size()+categoryProduct.size()>=4){
            throw new Exception("Product can only have 4 category");
        }
        //check categories
        this.checkCategories(categories);

        //SAVING THE UPDATES TO DATABASE
        Product saveProduct = productRepository.save(updateProduct);

        this.addCategory(categories,saveProduct);

        return saveProduct;
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

    public void addCategory(List<String> categories, Product newProduct)throws Exception{
        categories.forEach(category->{
            try {
                Category categoryGet = categoryRepository.findByNameIgnoreCase(category).orElseThrow(()->new Exception("Category not found"));
                ProductCategory addCategory = new ProductCategory();
                addCategory.setCategory(categoryGet);
                addCategory.setProduct(newProduct);
                productCategoryRepository.save(addCategory);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void checkCategories(List<String> categories){
        categories.forEach(category->{
            try {
                categoryRepository.findByNameIgnoreCase(category).orElseThrow(()->new Exception("Category not found"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


}
