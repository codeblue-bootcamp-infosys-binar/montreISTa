package com.codeblue.montreISTA.service;



import com.codeblue.montreISTA.DTO.PhotoPostDTO;
import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
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

}
