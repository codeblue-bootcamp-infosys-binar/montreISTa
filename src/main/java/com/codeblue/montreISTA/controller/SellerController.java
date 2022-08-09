package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.SellerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class SellerController {

    @Autowired
    SellerServiceImpl sellerServiceImpl;

    @Autowired
    UserRepository userRepository;

    //GET ALL
    @GetMapping("/sellers")
    public ResponseEntity<Object> getAllSeller(){
        try{
            List<Seller> sellers = sellerServiceImpl.findAllSeller();

            return ResponseHandler.generateResponse("successfully retrieved sellers", HttpStatus.OK, sellers);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/sellers/Username")
    public ResponseEntity<Object> findByUsername(@Param("keyword") String keyword){
        try{
            List<Seller> sellers = sellerServiceImpl.findByUsername(keyword);

            return ResponseHandler.generateResponse("successfully retrieved username", HttpStatus.OK, sellers);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    //GET ALL BY SELLER ID
    @GetMapping("/sellers/store/{seller_id}")
    public ResponseEntity<Object> getAllSellerBySellerId(@PathVariable("seller_id") Long sellerId){
        try{
            List<Seller> seller = sellerServiceImpl.findSellertBySellerId(sellerId);
            return ResponseHandler.generateResponse("successfully retrieved sellers", HttpStatus.OK, seller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/sellers/{id}")
    public ResponseEntity<Object> getSellerById(@PathVariable("id") Long id){
        try{
            Optional<Seller> seller = sellerServiceImpl.findSellerById(id);
            return ResponseHandler.generateResponse("successfully retrieved seller", HttpStatus.OK, seller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/sellers/create")
    public ResponseEntity<Object> createSeller(@RequestBody SellerRequestDTO sellerRequestDTO){
        try {

            Seller seller = sellerServiceImpl.createSeller(sellerRequestDTO);
            return ResponseHandler.generateResponse("successfully retrieved seller", HttpStatus.CREATED, seller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/sellers/update/{id}")
    public ResponseEntity<Object> updateSeller(@RequestBody SellerRequestDTO sellerRequestDTO, @PathVariable("id") Long id){
        try{
            Optional<Seller> targetSeller = sellerServiceImpl.findSellerById(id);
            Seller updateSeller = targetSeller.get();
            updateSeller.setSellerId(id);
            updateSeller.setStoreName(sellerRequestDTO.getStoreName());
            updateSeller.setStoreAddress(sellerRequestDTO.getStoreAddress());
            updateSeller.setStorePhoto(sellerRequestDTO.getStorePhoto());

            sellerServiceImpl.updateSeller(updateSeller);
            return ResponseHandler.generateResponse("successfully updated Seller", HttpStatus.CREATED, updateSeller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/sellers/delete/{id}")
    public ResponseEntity<Object> deleteSeller(@PathVariable("id") Long id){
        try{
            sellerServiceImpl.deleteSeller(id);
            return ResponseHandler.generateResponse("successfully deleted seller", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }
}

