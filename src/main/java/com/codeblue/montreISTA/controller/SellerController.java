package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.SellerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="02. Seller")
@SecurityRequirement(name = "bearer-key")
public class SellerController {

    private static final Logger logger =  LoggerFactory.getLogger(SellerController.class);
    private static final String Line = "====================";
    private final SellerService sellerService;


    //CREATE
    @PostMapping("/user/sellers/create-shop")
    public ResponseEntity<Object> createSeller(@RequestBody SellerRequestDTO sellerRequestDTO, Authentication authentication) throws Exception {
            return sellerService.createSeller(sellerRequestDTO,authentication);
    }

    @PostMapping(value="/user/upload-photo-store",consumes ="multipart/form-data" )
    public ResponseEntity<Object> postPhotoStore(@RequestParam ("file") MultipartFile file, Authentication authentication) throws Exception {
           return sellerService.uploadPhotoStore(authentication,file);
    }
    @GetMapping("/user/my-store")
    public ResponseEntity<Object> getMyStore(Authentication authentication) throws Exception {
           return sellerService.findByUsername(authentication.getName());
    }

    @GetMapping("/user/login-as-seller")
    public ResponseEntity<Object> loginAsSeller(Authentication authentication,
                                                @RequestParam(required = false) String sort,
                                                @RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) boolean descending) throws Exception {
           return sellerService.loginAsSeller(authentication.getName(),page,sort,descending);
    }
    //UPDATE
    @PutMapping("/user/sellers/edit-store-profile")
    public ResponseEntity<Object> updateSeller(@RequestBody SellerRequestDTO sellerRequestDTO, Authentication authentication) throws Exception {
            return sellerService.updateSeller(sellerRequestDTO,authentication);
    }

    //GET ALL
    @GetMapping("/dashboard/sellers")
    public ResponseEntity<Object> getAllSeller(){
           return sellerService.findAllSeller();
    }

    @GetMapping("/dashboard/sellers/{id}")
    public ResponseEntity<Object> findByUsername(@PathVariable("id") Long id) throws Exception {
           return sellerService.findSellerById(id);

    }

    //DELETE
    @DeleteMapping("/user/sellers/delete/{id}")
    public ResponseEntity<Object> deleteSeller(@PathVariable("id") Long id,Authentication authentication) throws Exception {
          return sellerService.deleteSeller(id,authentication);
    }
}

