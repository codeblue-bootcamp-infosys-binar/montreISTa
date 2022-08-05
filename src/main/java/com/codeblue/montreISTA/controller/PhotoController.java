package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.PhotoPostDTO;
import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;

import com.codeblue.montreISTA.service.PhotoServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
public class PhotoController {
    private PhotoService photoService;

    /**
     * FindAll
     * @return
     */
    @GetMapping("/photo")
    public ResponseEntity<Object> findAllPhoto(){
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
    @GetMapping("/photo/name")
    public ResponseEntity<Object> findByname(@Param("keyword") String keyword){
        try{
        List<PhotoResponseDTO> results = photoService.findByUsername(keyword);
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * findBy Photoname
     * @param keyword
     * @return
     */
    @GetMapping("/photo/photoname")
    public ResponseEntity<Object> findByPhotoName(@Param("keyword") String keyword){
        try {
            List<PhotoResponseDTO> results = photoService.findByPhotoName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * find by Product.Productname
     * @param keyword
     * @return
     */
    @GetMapping("/photo/productname")
    public ResponseEntity<Object> findByProductname(@Param("keyword") String keyword){
        try{
        List<PhotoResponseDTO> results = photoService.findByProductName(keyword);
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
            PhotoPostDTO results = photoService.createPhoto(photo);
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
    public ResponseEntity<Object> deletePhoto(@PathVariable Long id){
       try{
           photoService.deleteById(id);
        return ResponseHandler.generateResponse("successfully delete product", HttpStatus.OK, "deleted");
    } catch (Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
    }
}



}
