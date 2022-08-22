package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.helper.Pagination;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final RoleRepository roleRepository;

    public List<Product> findAllProduct(Integer page, String sort, boolean descending)throws Exception {
        Pageable pageable = Pagination.paginate(page, sort, descending);
        List<Product> products = productRepository.findAll(pageable).getContent();
        if(products.isEmpty()){
            throw new Exception("Product not found");
        }
        return products;
    }

    @Override
    public Product findBySellerUsername(String keyword) throws Exception{
        return productRepository.findFirstBySellerUserUsernameOrderByIdDesc(keyword)
                .orElseThrow(()->new Exception("Product not found"));
    }

    public Product findProductById(Long id) throws Exception{

        return productRepository.findById(id).orElseThrow(()->new Exception("Product not found"));
    }

    @Override
    public List<Product> findByProductName(String name, Integer page, String sort, boolean descending)  throws Exception{
        
        Pageable pageable = Pagination.paginate(page, sort, descending);

        return productRepository.findByProductNameIgnoreCaseContaining(name, pageable);
    }

    @Override
    public List<Product> findBySellerName(String name, Integer page, String sort, boolean descending) {

        Pageable pageable = Pagination.paginate(page, sort, descending);

        return productRepository.findBySellerUserNameIgnoreCaseContaining(name, pageable);
    }

    @Override
    public List<Product> findByStoreName(String name, Integer page, String sort, boolean descending) {

        Pageable pageable = Pagination.paginate(page, sort, descending);

        return productRepository.findBySellerStoreNameIgnoreCaseContaining(name, pageable);
    }

    @Override
    public List<Product> findByCategoryId(Long id, Integer page, String sort, boolean descending) {
    
        Pageable pageable = Pagination.paginate(page, sort, descending);

        return productRepository.findByCategoriesCategoryCategoriesId(id, pageable);
    }


    @Override
    public List<Product> findByCategoryName(String name, Integer page, String sort, boolean descending) {

        Pageable pageable = Pagination.paginate(page, sort, descending);

        return productRepository.findByCategoriesCategoryNameIgnoreCaseContaining(name, pageable);
    }

    @Override
    public List<Product> findProductBySellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception{

        Pageable pageable = Pagination.paginate(page, sort, descending);

        Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as seller"));
        List<Product> product = productRepository.findBySellerSellerId(seller.getSellerId(), pageable);
        if(product.isEmpty()){
            throw new Exception("You don't have a product");
        }
        return product;
    }

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO,Authentication authentication) throws Exception{
        Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));

        Pageable pageable = Pagination.paginate(0,"price", false);
        List<Product> products = productRepository.findBySellerSellerId(seller.getSellerId(), pageable);
        if(productRequestDTO.getCategory().size()>4){
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
        Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));
        //GET SELLER FROM DATABASE BY ID
        Product product = productRequestDTO.convertToEntity(seller);
        Product updateProduct = productRepository.findById(id).orElseThrow(()->new Exception("Product Not Found"));


        //UPDATING PRODUCT DATA
        updateProduct.setId(id);
        updateProduct.setProductName(product.getProductName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());

        List<String> categories = productRequestDTO.getCategory();
        if(categories.isEmpty()){
            throw new Exception("Product must have 1 category");
        }
        List<Category> categoryProduct = categoryRepository.findByProductsProductId(id);
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
        Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));
        Product productById = productRepository.findById(id).orElseThrow(()->new Exception("Product Not Found"));
        if(seller!=productById.getSeller()){
            throw new Exception("You only can delete your product");
        }
        productRepository.deleteById(id);
    }

    public void addCategory(List<String> categories, Product newProduct) {
        categories.forEach(category->{
            try {
                List<ProductCategory> productCategories = productCategoryRepository.findByCategoryNameIgnoreCase(category);
                boolean checkCategory = productCategories.stream().anyMatch(productCategory -> Objects.equals(productCategory.getProduct().getId(), newProduct.getId()));
                Category categoryGet = categoryRepository.findByNameIgnoreCase(category).orElseThrow(()->new Exception("Category not found"));
                if(!checkCategory) {
                    ProductCategory addCategory = new ProductCategory();
                    addCategory.setCategory(categoryGet);
                    addCategory.setProduct(newProduct);
                    productCategoryRepository.save(addCategory);
                }
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
