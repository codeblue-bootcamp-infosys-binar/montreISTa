package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.DTO.PhotoPostDTO;
import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;

import java.util.List;

public interface PhotoServices {
    List<PhotoResponseDTO> findAll();
    List<PhotoResponseDTO> findByPhotoName(String photoName);
    PhotoResponseDTO createPhoto(PhotoRequestDTO photoRequestDTO);
    PhotoResponseDTO updatePhoto(Photo photo,Long id);
    void deleteById(Long id);
}
