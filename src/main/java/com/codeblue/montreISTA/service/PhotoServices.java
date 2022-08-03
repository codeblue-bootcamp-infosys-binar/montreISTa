package com.codeblue.montreISTA.service;


import DTO.PhotoPostDTO;
import DTO.PhotoRequestDTO;
import DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;

import java.util.List;

public interface PhotoServices {
    List<PhotoResponseDTO> findAll();
    List<PhotoResponseDTO> findByPhotoName(String photoName);
    PhotoPostDTO createPhoto(PhotoRequestDTO photoRequestDTO);
    PhotoPostDTO updatePhoto(Photo photo,Long id);
    void deleteById(Long id);
}
