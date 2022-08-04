package com.codeblue.montreISTA.service;



import com.codeblue.montreISTA.DTO.PhotoPostDTO;
import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;

import java.util.List;

public interface PhotoService {
    List<PhotoResponseDTO> findAll();
    List<PhotoResponseDTO> findByPhotoName(String photoName)throws Exception;

    List<PhotoResponseDTO> findByProductName(String productName)throws Exception;
    List<PhotoResponseDTO> findByUsername(String name)throws Exception;

    PhotoPostDTO createPhoto(PhotoRequestDTO photoRequestDTO);
    PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO,Long id) throws Exception;

    void deleteById(Long id);

}
