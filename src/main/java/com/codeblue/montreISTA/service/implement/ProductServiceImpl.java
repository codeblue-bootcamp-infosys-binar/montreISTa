package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.controller.ProductController;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.helper.Pagination;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final RoleRepository roleRepository;
    private final PhotoRepository photoRepository;
    private final DTOConverter dtoConverter;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final String Line = "====================";
    private static final String productPhotoDefault = "https://cdn5.vectorstock.com/i/1000x1000/38/19/product-promotion-black-icon-concept-vector-29963819.jpg";


    public ResponseEntity<Object> findAllProduct(Integer page, String sort, boolean descending) {
        try {
            sort = this.sort(sort);
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<Product> products = productRepository.findAll(pageable).getContent();
            if (products.isEmpty()) {
                throw new Exception("Product not found");
            }
            logger.info("==================== Logger Start Get All Products     ====================");
            for (Product productData : products) {
                logger.info("-------------------------");
                logger.info("Product ID    : " + productData.getId());
                logger.info("Description   : " + productData.getDescription());
                logger.info("Price         : " + productData.getPrice());
                logger.info("Product name  : " + productData.getProductName());
                logger.info("Seller ID     : " + productData.getSeller());
            }
            logger.info("==================== Logger End Get All Products    ====================");
            logger.info(" ");

            List<ProductResponseDTO> results = dtoConverter.convertProducts(products)
                    .stream()
                    .filter(product->product.getStock()>0)
                    .collect(Collectors.toList());
            return ResponseHandler.generateResponse("successfully get all products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Products     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!")));
            logger.info("==================== Logger End Get All Products     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public Product findBySellerUsername(String keyword) throws Exception {
        return productRepository.findFirstBySellerUserUsernameOrderByIdDesc(keyword)
                .orElseThrow(() -> new Exception("Product not found"));
    }

    public ResponseEntity<Object> findProductById(Long id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
            if(product.getStock()<=0){
                throw new Exception("This product have no stock");
            }
            ProductResponseDTO result = dtoConverter.convertOneProducts(product);
            logger.info(Line + "Logger Start Get product id " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Get product id " + Line);
            return ResponseHandler.generateResponse("successfully get product", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByProductName(String name, Integer page, String sort, boolean descending) throws Exception {
        try {
            sort = this.sort(sort);
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<Product> products = productRepository.findByProductNameIgnoreCaseContaining(name, pageable);
            if (products.isEmpty()) {
                throw new Exception("Product not found");
            }
            List<ProductResponseDTO> results = dtoConverter.convertProducts(products)
                    .stream()
                    .filter(product->product.getStock()>0)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get product name " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get product name " + Line);
            return ResponseHandler.generateResponse("successfully get product", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findBySellerName(String name, Integer page, String sort, boolean descending) {
        try {
            sort = this.sort(sort);
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<Product> products = productRepository.findBySellerUserUsernameIgnoreCaseContaining(name, pageable);
            if (products.isEmpty()) {
                throw new Exception("Product not found");
            }
            List<ProductResponseDTO> results = dtoConverter.convertProducts(products)
                    .stream()
                    .filter(product->product.getStock()>0)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get seller name " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get seller name " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByStoreName(String name, Integer page, String sort, boolean descending) {
        try {
            sort = this.sort(sort);
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<Product> products = productRepository.findBySellerStoreNameIgnoreCaseContaining(name, pageable);
            if (products.isEmpty()) {
                throw new Exception("Product not found");
            }
            List<ProductResponseDTO> results = dtoConverter.convertProducts(products)
                    .stream()
                    .filter(product->product.getStock()>0)
                    .collect(Collectors.toList());

            logger.info(Line + "Logger Start Get store name " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get store name " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByCategoryId(Long id, Integer page, String sort, boolean descending) {
        try {
            sort = this.sort(sort);
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<Product> products = productRepository.findByCategoriesCategoryCategoriesId(id, pageable);
            if (products.isEmpty()) {
                throw new Exception("Product not found");
            }
            List<ProductResponseDTO> results = dtoConverter.convertProducts(products)
                    .stream()
                    .filter(product->product.getStock()>0)
                    .collect(Collectors.toList());

            logger.info(Line + "Logger Start Get By Category Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Category Id " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }


    @Override
    public ResponseEntity<Object> findByCategoryName(String name, Integer page, String sort, boolean descending) {
        try {
            sort = this.sort(sort);
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<Product> products = productRepository.findByCategoriesCategoryNameIgnoreCaseContaining(name, pageable);
            if (products.isEmpty()) {
                throw new Exception("Product not found");
            }
            List<ProductResponseDTO> results = dtoConverter.convertProducts(products)
                    .stream()
                    .filter(product->product.getStock()>0)
                    .collect(Collectors.toList());

            logger.info(Line + "Logger Start Get category name " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get category name " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findProductBySellerId(Authentication authentication, Integer page, String sort, boolean descending) {
        try {
            sort = this.sort(sort);
            Pageable pageable = Pagination.paginate(page, sort, descending);
            Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please login as seller"));
            List<Product> product = productRepository.findBySellerSellerId(seller.getSellerId(), pageable);
            if (product.isEmpty()) {
                throw new Exception("You don't have a product");
            }

            List<ProductResponseDTO> results = dtoConverter.convertProducts(product);

            logger.info(Line + "Logger Start Get seller id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get seller id " + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> search(String keyword, Integer page, String sort, boolean descending) throws Exception {
        try {
            if (sort != null) {
                switch (sort) {
                    case "id" -> sort = "product.id";
                    case "price" -> sort = "product.price";
                    case "productName" -> sort = "product.productName";
                    case "description" -> sort = "product.description";
                    case "sellerId" -> sort = "product.seller.sellerId";
                    case "storeName" -> sort = "product.seller.storeName";
                    case "categories" -> sort = "category.name";
                    default -> sort = "id";
                }
            }
            if(keyword.toUpperCase().equals("HAPPINESS")){
                throw new Exception("maaf anda kurang beruntung");
            }
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<ProductCategory> productCategories = productCategoryRepository.search(keyword.toUpperCase(), pageable).getContent();
            if (productCategories.isEmpty()) {
                throw new Exception("Product Not found");
            }
            List<ProductResponseDTO> results = productCategories.stream()
                    .map(ProductCategory::getProduct)
                    .distinct()
                    .map(dtoConverter::convertOneProducts)
                    .filter(product->product.getStock()>0)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Search" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Search" + Line);
            return ResponseHandler.generateResponse("successfully get products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> createProduct(ProductRequestDTO productRequestDTO, Authentication authentication) {
        try {
            Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("You don't have store"));
            Pageable pageable = Pagination.paginate(0, "price", false);
            List<Product> products = productRepository.findBySellerSellerId(seller.getSellerId(), pageable);
            if (productRequestDTO.getProductName() == null
                    || productRequestDTO.getDescription() == null
                    || productRequestDTO.getCategory() == null
                    || productRequestDTO.getPrice() == null
                    || productRequestDTO.getStock() == null) {
                throw new Exception("Please check again your input, it can't empty");
            }
            if (productRequestDTO.getPrice() <= 0||productRequestDTO.getStock() <= 0) {
                throw new Exception("Price or stock can't be 0 or negatif");
            }
            if (productRequestDTO.getCategory().size() > 4) {
                throw new Exception("Product can only have 4 category");
            }

            if (products.size() >= 4) {
                throw new Exception("User can only have 4 Products");
            }

            List<String> categories = productRequestDTO.getCategory();
            if (categories.isEmpty()) {
                throw new Exception("Product must have 1 category");
            }

            //check categories
            this.checkCategories(categories);

            Product newProduct = productRequestDTO.convertToEntity(seller);
            Product saveProduct = productRepository.save(newProduct);

            this.addCategory(categories, saveProduct);
            List<Photo> photos = new ArrayList<>();
            Photo photo = this.addPhoto(saveProduct);
            photos.add(photo);
            saveProduct.setPhotos(photos);
            ProductResponseDTO result = dtoConverter.convertOneProducts(saveProduct);

            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Create " + Line);

            return ResponseHandler.generateResponse("successfully create product", HttpStatus.CREATED, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create product!");
        }
    }

    @Override
    public ResponseEntity<Object> updateProduct(ProductRequestDTO productRequestDTO, Long id, Authentication authentication) {
        try {
            if (productRequestDTO.getProductName() == null
                    || productRequestDTO.getDescription() == null
                    || productRequestDTO.getPrice() == null
                    || productRequestDTO.getStock() == null) {
                throw new Exception("Please check again your input, it can't empty");
            }
            if (productRequestDTO.getPrice() <= 0||productRequestDTO.getStock() <= 0) {
                throw new Exception("Price or stock can't be 0 or negatif");
            }
            Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("You don't have store"));
            Product product = productRepository.findById(id).orElseThrow(() -> new Exception("Product Not Found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkUser = product.getSeller().getUser().getUsername().equals(authentication.getName());
            Product productRequest = productRequestDTO.convertToEntity(seller);
            Product saveProduct;
            if (checkRoles || checkUser) {

                //UPDATING PRODUCT DATA
                product.setId(id);
                product.setProductName(productRequest.getProductName());
                product.setDescription(productRequest.getDescription());
                product.setPrice(productRequest.getPrice());

                //check categories
                if (productRequestDTO.getCategory() != null) {
                    List<String> categories = productRequestDTO.getCategory();
                    List<Category> categoryProduct = categoryRepository.findByProductsProductId(id);
                    if (categories.size() + categoryProduct.size() >= 4) {
                        throw new Exception("Product can only have 4 category");
                    }
                    this.checkCategories(categories);
                    saveProduct = productRepository.save(product);
                    this.addCategory(categories, saveProduct);
                } else {
                    saveProduct = productRepository.save(product);
                }
                ProductResponseDTO results = dtoConverter.convertOneProducts(saveProduct);
                logger.info(Line + "Logger Start Update By Id " + Line);
                logger.info(String.valueOf(results));
                logger.info(Line + "Logger End Update By Id " + Line);
                return ResponseHandler.generateResponse("successfully updated product", HttpStatus.CREATED, results);

            } else {
                throw new Exception("You can't update other product");
            }
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update product!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(Long id, Authentication authentication) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new Exception("Product Not Found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkUser = product.getSeller().getUser().getUsername().equals(authentication.getName());

            if (checkRoles || checkUser) {
                productRepository.deleteById(id);
                logger.info(Line + "Logger Start Delete By Id " + Line);
                logger.info("Delete Success");
                logger.info(Line + "Logger End Delete By Id " + Line);
                return ResponseHandler.generateResponse("successfully deleted product", HttpStatus.OK, null);
            } else {
                throw new Exception("You can't delete other product");
            }

        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete product!");
        }
    }

    public void addCategory(List<String> categories, Product newProduct) {
        categories.forEach(category -> {
            try {
                List<ProductCategory> productCategories = productCategoryRepository.findByCategoryNameIgnoreCase(category);
                boolean checkCategory = productCategories.stream().anyMatch(productCategory -> Objects.equals(productCategory.getProduct().getId(), newProduct.getId()));
                Category categoryGet = categoryRepository.findByNameIgnoreCase(category).orElseThrow(() -> new Exception("Category not found"));
                if (!checkCategory) {
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

    public Photo addPhoto(Product newProduct) {
        Photo photo = new Photo();
        photo.setPhotoURL(productPhotoDefault);
        photo.setProduct(newProduct);
        return photoRepository.save(photo);
    }

    public void checkCategories(List<String> categories) {
        categories.forEach(category -> {
            try {
                categoryRepository.findByNameIgnoreCase(category).orElseThrow(() -> new Exception("Category not found"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String sort(String sort) {
        if (sort != null) {
            switch (sort) {
                case "id" -> sort = "productId";
                case "sellerId" -> sort = "seller.sellerId";
                case "storeName" -> sort = "seller.storeName";
            }
        }
        return sort;
    }


}
