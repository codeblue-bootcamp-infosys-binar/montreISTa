package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;

import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.service.CloudinaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PhotoService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name="04. Photo")
public class PhotoController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    private static final String Line = "====================";
    private final PhotoService photoService;
    private final CloudinaryService cloudinaryService;
    /**
     * FindAll
     * @return
     */
    @GetMapping("/photos")
    public ResponseEntity<Object> findAll(){
        try {
            List<PhotoResponseDTO> photos = photoService.findAll();
            List<PhotoResponseDTO> results = photoService.findAll();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Photo     ====================");
            for(PhotoResponseDTO photoData : photos){
                Map<String, Object> photo = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Photo ID      : " + photoData.getPhoto_id());
                logger.info("Photo Name    : " + photoData.getPhoto_name());
                logger.info("Photo Url     : " + photoData.getPhoto_url());
                logger.info("Product ID    : " + photoData.getProduct_id());
                photo.put("Photo ID          ", photoData.getPhoto_id());
                photo.put("Photo Name        ", photoData.getPhoto_name());
                photo.put("Photo Url         ", photoData.getPhoto_url());
                maps.add(photo);
            }
            logger.info("==================== Logger End Get All Photo   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            logger.info("==================== Logger Start Get All Photo     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Product had no value!")));
            logger.info("==================== Logger End Get All Photo     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Product had no value!");
        }
    }

    /**
     * find by seller.name/username
     * @param keyword
     * @return
     */
    @GetMapping("/photo/sellername")
    public ResponseEntity<Object> findBySellerName(@Param("keyword") String keyword){
        try{
        List<PhotoResponseDTO> results = photoService.findBySellerName(keyword);
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

    /**
     * Create Photo
     * @param photo
     * @return
     */
    @PostMapping("/photo")
    public ResponseEntity<Object> postPhoto(@RequestBody PhotoRequestDTO photo) {
        try {
            PhotoResponseDTO results = photoService.createPhoto(photo);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create product", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed post photo!");
        }
    }

    @PostMapping(value="/photoCloudinary",consumes = "multipart/form-data")
    public ResponseEntity<Object> postPhoto(@RequestParam ("file") MultipartFile file,
                                            @RequestParam String photoName,
                                            @RequestParam Long id) throws IOException {
        try {
            PhotoRequestDTO photo = new PhotoRequestDTO();
            String url = cloudinaryService.uploadFile(file);
            photo.setPhoto_name(photoName);
            photo.setPhoto_url(url);
            photo.setProduct_id(id);
            PhotoResponseDTO results = photoService.createPhoto(photo);
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
     * @param id
     * @param photo
     * @return
     */
    @PutMapping("/photo/{id}")
    public ResponseEntity<Object> updatePhoto(@PathVariable Long id, @RequestBody PhotoRequestDTO photo){
    try{
        PhotoResponseDTO results = photoService.updatePhoto(photo,id);
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


    @DeleteMapping("/photo/{id}")
    public ResponseEntity<Object> deletePhoto(@RequestParam Long id){
       try{
           photoService.deleteById(id);
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



}
