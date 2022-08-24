package com.codeblue.montreISTA.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {
    ResponseEntity<Object> findAll()throws Exception;
    ResponseEntity<Object> findBySellerName(Authentication authentication)throws Exception;
    ResponseEntity<Object> findById(Long id)throws Exception;
    ResponseEntity<Object> findBySellerId(Long id)throws Exception;
    ResponseEntity<Object> findByProductId(Long id)throws Exception;

    ResponseEntity<Object> createPhoto(Long productId,List<MultipartFile> files, Authentication authentication)throws Exception;
    ResponseEntity<Object> updatePhoto(MultipartFile file,Long productId, Long photoId,Authentication authentication) throws Exception;
    ResponseEntity<Object> deleteById(Long id, Authentication authentication)throws Exception;

}
