package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;

import com.codeblue.montreISTA.service.CloudinaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="04. Photo")
public class PhotoController {
    private final PhotoService photoService;
    private final CloudinaryService cloudinaryService;
    /**
     * FindAll
     * @return
     */
    @GetMapping("/photos")
    public ResponseEntity<Object> findAll(){
        try {
            List<PhotoResponseDTO> results = photoService.findAll();
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
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
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
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
            return ResponseHandler.generateResponse("successfully create product", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
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
            return ResponseHandler.generateResponse("successfully create product", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
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
        return ResponseHandler.generateResponse("successfully update product", HttpStatus.OK, results);
    } catch (Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping("/photo/{id}")
    public ResponseEntity<Object> deletePhoto(@RequestParam Long id){
       try{
           photoService.deleteById(id);
        return ResponseHandler.generateResponse("successfully delete product", HttpStatus.OK, "deleted");
    } catch (Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
    }
}



}
