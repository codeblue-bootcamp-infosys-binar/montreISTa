package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.DTO.ProductRequestDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.entity.Category;
import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CategoryService;
import com.codeblue.montreISTA.service.PhotoServiceImp;
import com.codeblue.montreISTA.service.ProductServiceImpl;
import com.codeblue.montreISTA.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;
    @Autowired
    SellerService sellerService;
    @Autowired
    CategoryService categoryService;


//==============================


    //GET ALL PRODUCTS
    @GetMapping("/products")
    public ResponseEntity<Object> getAllProduct(){
        try{
            List<Product> products = productServiceImpl.findAllProduct();
            List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();

            for(Product product : products){

                List<PhotoProductDTO> photosDTO = new ArrayList<>();

                for(Photo photo : product.getPhotos()){
                    PhotoProductDTO photoConvert = photo.convertToProduct();
                    photosDTO.add(photoConvert);
                }

                List<Category> categories = categoryService.findByProductId(product.getProductId());
                List<String> categoriesDTO = new ArrayList<>();
                for(Category category: categories){
                    String categoryDTO = category.getName();
                    categoriesDTO.add(categoryDTO);
                }

                ProductResponseDTO productDTO = product.convertToResponse(photosDTO, categoriesDTO);
                productResponseDTOS.add(productDTO);

            }

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }


    //GET ALL PRODUCTS BY SELLER ID
    @GetMapping("/products/store/{seller_id}")
    public ResponseEntity<Object> getAllProductBySellerId(@PathVariable("seller_id") Long sellerId){
        try{
            List<Product> products = productServiceImpl.findProductBySellerId(sellerId);
            List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();

            for(Product product : products){
                List<PhotoProductDTO> photoDTOS = new ArrayList<>();

                for (Photo photo : product.getPhotos()){
                    PhotoProductDTO photoConvert = photo.convertToProduct();
                    photoDTOS.add(photoConvert);
                }
                ProductResponseDTO productDTO = product.convertToResponse(photoDTOS, null);
                productResponseDTOS.add(productDTO);
            }

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, null);
        }
    }

    //GET ONE PRODUCT BY ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id){
        try{

            //RETRIEVING PRODUCT FROM DATABASE
            Optional<Product> product = productServiceImpl.findProductById(id);

            //CONVERTING PHOTO RAW ENTITY DATA TO PHOTO WITH DTO
            List<Photo> photos = product.get().getPhotos();
            List<PhotoProductDTO> photosDTO = new ArrayList<>();
            for(Photo photo : photos){
                PhotoProductDTO photoConvert = photo.convertToProduct();
                photosDTO.add(photoConvert);
            }

            //CONVERTING CATEGORY RAW ENTITY DATA TO CATEGORY WITH DTO
            List<Category> categories = categoryService.findByProductId(product.get().getProductId());
            List<String> categoriesDTO = new ArrayList<>();
            for(Category category: categories){
                String categoryDTO = category.getName();
                categoriesDTO.add(categoryDTO);
            }

            //INSERTING PHOTO & CATEGORY WITH DTO INTO PRODUCT
            //CONVERTING PRODUCT INTO PRODUCT DTO
            ProductResponseDTO productResponseDTO = product.get().convertToResponse(photosDTO,categoriesDTO);

            //RETURN
            return ResponseHandler.generateResponse("successfully retrieved product", HttpStatus.OK, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    //GET BY PRODUCT NAME
    @GetMapping("/products/productname")
    public ResponseEntity<Object> getAllProductByProductName(@RequestParam String productName){
        try{
            List<Product> products = productServiceImpl.findByProductName(productName);
            List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
            for(Product product : products){

                List<PhotoProductDTO> photoDTOS = new ArrayList<>();
                for(Photo photo: product.getPhotos()){
                    PhotoProductDTO photoDTO = photo.convertToProduct();
                    photoDTOS.add(photoDTO);
                }

                ProductResponseDTO productDTO = product.convertToResponse(photoDTOS, null);
                productResponseDTOS.add(productDTO);
            }

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }

    //GET BY PRODUCT SELLER USERNAME
    @GetMapping("/products/sellername")
    public ResponseEntity<Object> getAllProductBySellerName(@RequestParam String sellername){
        try{
            List<Product> products = productServiceImpl.findBySellerName(sellername);
            List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();

            for(Product product : products){

                List<PhotoProductDTO> photoProductDTOS = new ArrayList<>();
                for(Photo photo : product.getPhotos()){
                    PhotoProductDTO photoDTO = photo.convertToProduct();
                    photoProductDTOS.add(photoDTO);
                }

                ProductResponseDTO productDTO = product.convertToResponse(photoProductDTOS, null);
                productResponseDTOS.add(productDTO);
            }

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }

    //GET BY STORE NAME
    @GetMapping("/products/storename")
    public ResponseEntity<Object> getAllProductByStoreName(@RequestParam String storeName){
        try{
            List<Product> products = productServiceImpl.findByStoreName(storeName);
            List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();

            for(Product product : products){
                List<PhotoProductDTO> photoProductDTOS = new ArrayList<>();

                for(Photo photo : product.getPhotos()){
                    PhotoProductDTO photoDTO = photo.convertToProduct();
                    photoProductDTOS.add(photoDTO);
                }

                ProductResponseDTO productDTO = product.convertToResponse(photoProductDTOS, null);
                productResponseDTOS.add(productDTO);
            }

            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, productResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,null);
        }
    }

    //CREATE PRODUCT
    @PostMapping("/products/create")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        try {
            Optional<Seller> productSeller = sellerService.findSellerById(productRequestDTO.getSellerId());
            Seller seller = productSeller.get();
            Product newProduct = productRequestDTO.convertToEntity(seller);
            productServiceImpl.createProduct(newProduct);

            ProductResponseDTO productResponseDTO = newProduct.convertToResponse(null, null);

            return ResponseHandler.generateResponse("successfully retrieved product", HttpStatus.CREATED, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE PRODUCT
    @PutMapping("/products/update/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductRequestDTO productRequestDTO, @PathVariable("id") Long id){
        try{
            //GET SELLER FROM DATABASE BY ID
            Optional<Seller> productSeller = sellerService.findSellerById(productRequestDTO.getSellerId());
            Seller seller = productSeller.get();

            //CONVERT REQUEST DTO TO PRODUCT ENTITY
            Product product = productRequestDTO.convertToEntity(seller);

            Optional<Product> targetProduct = productServiceImpl.findProductById(id);
            Product updateProduct = targetProduct.get();

            //UPDATING PRODUCT DATA
            updateProduct.setProductId(id);
            updateProduct.setSeller(product.getSeller());
            updateProduct.setProductName(product.getProductName());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setPrice(product.getPrice());

            //SAVING THE UPDATES TO DATABASE
            productServiceImpl.updateProduct(updateProduct);

            //LOOPING THROUGH PHOTO TO CONVERT PHOTO WITH DTO
            List<PhotoProductDTO> photoProductDTOS = new ArrayList<>();
            for(Photo photo : product.getPhotos()){
                PhotoProductDTO photoDTO = photo.convertToProduct();
                photoProductDTOS.add(photoDTO);
            }

            //CONVERTING PRODUCT TO RESPONSE DTO
            ProductResponseDTO productResponseDTO = updateProduct.convertToResponse(photoProductDTOS, null);

            return ResponseHandler.generateResponse("successfully updated product", HttpStatus.CREATED, productResponseDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE PRODUCT
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id){
        try{
            productServiceImpl.deleteProduct(id);
            return ResponseHandler.generateResponse("successfully deleted product", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }
}