package com.codeblue.montreISTA.service;



import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;

import java.util.List;

public interface PhotoService {
    List<PhotoResponseDTO> findAll();


    List<PhotoResponseDTO> findBySellerName(String name)throws Exception;
    PhotoResponseDTO findById(Long id)throws Exception;
    List<PhotoResponseDTO> findBySellerId(Long id)throws Exception;
    List<PhotoResponseDTO> findByProductId(Long id)throws Exception;

    PhotoResponseDTO createPhoto(PhotoRequestDTO photoRequestDTO)throws Exception;
    PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO,Long id) throws Exception;

    void deleteById(Long id);

}
