package com.codeblue.montreISTA.service;


import DTO.PhotoPostDTO;
import DTO.PhotoRequestDTO;
import DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;

import java.util.List;

public interface PhotoServices {
    List<PhotoResponseDTO> findAll();
    List<PhotoResponseDTO> findByPhotoName(String photoName);

    List<PhotoResponseDTO> findByProductName(String productName);
    List<PhotoResponseDTO> findByUsername(String name);

    PhotoPostDTO createPhoto(PhotoRequestDTO photoRequestDTO);
    public PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO,Long id);

    void deleteById(Long id);
    long deleteByPhotoName(String photoName);
    void deletedByProductName(String name);
    void deleteByUsername(String name);
}
