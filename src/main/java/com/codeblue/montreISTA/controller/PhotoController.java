package com.codeblue.montreISTA.controller;

import DTO.PhotoPostDTO;
import DTO.PhotoRequestDTO;
import DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.service.PhotoServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PhotoController {
    private PhotoServices photoServices;

    @GetMapping("/photo")
    public List<PhotoResponseDTO> findAllPhoto(){
        return photoServices.findAll();
    }

    @PostMapping("/photo")
    public PhotoPostDTO postPhoto(@RequestBody PhotoRequestDTO photo){
        return photoServices.createPhoto(photo);
    }
}
