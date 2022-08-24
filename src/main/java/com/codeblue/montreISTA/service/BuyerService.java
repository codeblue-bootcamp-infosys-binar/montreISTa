package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;


public interface BuyerService {
    ResponseEntity<Object> findAllBuyer();

    ResponseEntity<Object> findBuyerById(Long id) throws Exception;

    ResponseEntity<Object> createBuyer(Authentication authentication,Integer page, String sort, boolean descending)throws Exception ;

    ResponseEntity<Object> deleteBuyer(Long id, Authentication authentication)throws Exception;

    ResponseEntity<Object> findByUsername(String keywoard)throws Exception;

}
