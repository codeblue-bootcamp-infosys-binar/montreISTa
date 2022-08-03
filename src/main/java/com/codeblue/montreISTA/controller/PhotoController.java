package com.codeblue.montreISTA.controller;

import DTO.PhotoPostDTO;
import DTO.PhotoRequestDTO;
import DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.service.PhotoServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PhotoController {
    private PhotoServices photoServices;

    @GetMapping("/photo")
    public List<PhotoResponseDTO> findAllPhoto(){

        return photoServices.findAll();
    }

    @PostMapping("/photo/photoname")
    public List<PhotoResponseDTO> findByPhotoName(@RequestBody PhotoRequestDTO photo){
        return photoServices.findByPhotoName(photo.getPhoto_name());
    }
// belum berhasil
//    @DeleteMapping("/photo/delete")
//    public ResponseEntity<String> deleteByPhotoName(@RequestBody PhotoRequestDTO photo){
//        return new ResponseEntity<String>(photoServices.deleteByPhotoName(photo.getPhoto_name())+"delete success", HttpStatus.OK);
//    }


    @PostMapping("/photo")
    public PhotoPostDTO postPhoto(@RequestBody PhotoRequestDTO photo){

        return photoServices.createPhoto(photo);
    }

    @PutMapping("/photo/{id}")
    public PhotoResponseDTO updatePhoto(@PathVariable Long id,@RequestBody PhotoRequestDTO photo){

        return photoServices.updatePhoto(photo,id);
    }

    @DeleteMapping("/photo/{id}")
    public void deletePhoto(@PathVariable Long id){
        photoServices.deleteById(id);
    }
}
