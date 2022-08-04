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
