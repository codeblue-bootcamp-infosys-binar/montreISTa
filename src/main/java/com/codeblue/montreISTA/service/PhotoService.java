package com.codeblue.montreISTA.service;



import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {
    List<PhotoResponseDTO> findAll()throws Exception;


    List<PhotoResponseDTO> findBySellerName(Authentication authentication)throws Exception;
    PhotoResponseDTO findById(Long id)throws Exception;
    List<PhotoResponseDTO> findBySellerId(Long id)throws Exception;
    List<PhotoResponseDTO> findByProductId(Long id)throws Exception;

    List<PhotoResponseDTO> createPhoto(Long productId,List<MultipartFile> files, Authentication authentication)throws Exception;
    PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO,Long id, Authentication authentication) throws Exception;

    void deleteById(Long id, Authentication authentication)throws Exception;

}
