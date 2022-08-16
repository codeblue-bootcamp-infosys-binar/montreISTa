package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.service.ProductService;
import com.codeblue.montreISTA.service.implement.CloudinaryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@Tag(name="04. Photo")
@SecurityRequirement(name = "bearer-key")
public class PhotoController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    private static final String Line = "====================";
    private final PhotoService photoService;
    private final CloudinaryService cloudinaryService;
    private final ProductService productService;



    /**
     * find by seller.name/username
     */
    @GetMapping("/user/my-photo-product")
    public ResponseEntity<Object> findBySellerName(Authentication authentication){
        try{
        List<PhotoResponseDTO> results = photoService.findBySellerName(authentication);
            logger.info(Line + "Logger Start Get By sellername " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By sellername " + Line);
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Photo had no value!");
        }
    }

    @PostMapping(value="/user/upload-photo/product-id",consumes ="multipart/form-data" )
    public ResponseEntity<Object> postPhotoProduct(@RequestParam ("file") List<MultipartFile> files,
                                                   @RequestParam Long productId,
                                                   Authentication authentication) throws IOException {
        try {
            List<PhotoResponseDTO> results = photoService.createPhoto(productId,files, authentication);
            logger.info(Line + "Logger Start Query " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Query " + Line);
            return ResponseHandler.generateResponse("successfully create product", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed photo cloud!");
        }
    }

    @PostMapping(value="/user/upload-photo/last-product-seller",consumes ="multipart/form-data" )
    public ResponseEntity<Object> postPhoto(@RequestParam ("file") List<MultipartFile> files,
                                            Authentication authentication) throws IOException {
        try {
            Product product = productService.findBySellerUsername(authentication.getName());
            List<PhotoResponseDTO> results = photoService.createPhoto(product.getProductId(),files, authentication);
            logger.info(Line + "Logger Start Query " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Query " + Line);
            return ResponseHandler.generateResponse("successfully create product", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed photo cloud!");
        }
    }

    /**
     * Update Photo
     * @param
     * @param
     * @return
     */
    @PutMapping(value = "/user/photo/edit/{photoId}",consumes = "multipart/form-data")
    public ResponseEntity<Object> updatePhoto(@RequestParam ("file") MultipartFile file,
                                              @PathVariable Long photoId,
                                              @RequestParam Long productId,
                                              Authentication authentication) throws IOException {
    try{
        PhotoRequestDTO photo = new PhotoRequestDTO();
        String url = cloudinaryService.uploadFile(file);
        photo.setPhoto_url(url);
        photo.setProduct_id(productId);
        PhotoResponseDTO results = photoService.updatePhoto(photo,photoId, authentication);
        logger.info(Line + "Logger Start By Id " + Line);
        logger.info(String.valueOf(results));
        logger.info(Line + "Logger End By Id " + Line);
        return ResponseHandler.generateResponse("successfully update product", HttpStatus.OK, results);
    } catch (Exception e) {
        logger.error(Line + " Logger Start Error " + Line);
        logger.error(e.getMessage());
        logger.error(Line + " Logger End Error " + Line);
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Photo had no value!");
        }
    }

    @DeleteMapping("/user/photo/delete/{id}")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long id, Authentication authentication){
       try{
           photoService.deleteById(id, authentication);
           logger.info(Line + "Logger Start Delete By Id " + Line);
           logger.info("Delete Success");
           logger.info(Line + "Logger End Delete By Id " + Line);
        return ResponseHandler.generateResponse("successfully delete product", HttpStatus.OK, "deleted");
    } catch (Exception e) {
           logger.error(Line + " Logger Start Error " + Line);
           logger.error(e.getMessage());
           logger.error(Line + " Logger End Error " + Line);
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete photo!");
        }
    }

    @GetMapping("/dashboard/photo/product/{id}")
    public ResponseEntity<Object> findByProduct(@PathVariable Long id){
        try{
            List<PhotoResponseDTO> results = photoService.findByProductId(id);
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/dashboard/photo/seller/{id}")
    public ResponseEntity<Object> findBySellerId(@PathVariable Long id){
        try{
            List<PhotoResponseDTO> results = photoService.findBySellerId(id);
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    /**
     * FindAll
     */
    @GetMapping("/dashboard/photos")
    public ResponseEntity<Object> findAll() {
        try {
            List<PhotoResponseDTO> results = photoService.findAll();
            logger.info("==================== Logger Start Get All Photo     ====================");
            for (PhotoResponseDTO photoData : results) {
                logger.info("-------------------------");
                logger.info("Photo ID      : " + photoData.getPhoto_id());

                logger.info("Photo Url     : " + photoData.getPhoto_url());
                logger.info("Product ID    : " + photoData.getProduct_id());
            }
            logger.info("==================== Logger End Get All Photo   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Photo     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!")));
            logger.info("==================== Logger End Get All Photo     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }




}
