package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;

    //GET ALL
    @GetMapping("/seller")
    public ResponseEntity<Object> getAllSeller(){
        try{
            List<Seller> sellers = sellerService.findAllSeller();

            return ResponseHandler.generateResponse("successfully retrieved sellers", HttpStatus.OK, sellers);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ALL BY SELLER ID
    @GetMapping("/sellers/store/{seller_id}")
    public ResponseEntity<Object> getAllSellerBySellerId(@PathVariable("seller_id") Long sellerId){
        try{
            List<Seller> seller = sellerService.findSellertBySellerId(sellerId);
            return ResponseHandler.generateResponse("successfully retrieved sellers", HttpStatus.OK, seller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/sellers/{id}")
    public ResponseEntity<Object> getSellerById(@PathVariable("id") Long id){
        try{
            Optional<Seller> seller = sellerService.findSellerById(id);
            return ResponseHandler.generateResponse("successfully retrieved seller", HttpStatus.OK, seller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/sellers/create")
    public ResponseEntity<Object> createSeller(@RequestBody Seller newSeller){
        try {
            Seller seller = sellerService.createSeller(newSeller);
            return ResponseHandler.generateResponse("successfully retrieved seller", HttpStatus.CREATED, seller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/sellers/update/{id}")
    public ResponseEntity<Object> updateSeller(@RequestBody Seller seller, @PathVariable("id") Long id){
        try{
            Optional<Seller> targetSeller = sellerService.findSellerById(id);
            Seller updateSeller = targetSeller.get();
            updateSeller.setSellerId(id);
            updateSeller.setStoreName(seller.getStoreName());
            updateSeller.setStoreAddress(seller.getStoreAddress());
            updateSeller.setStorePhoto(seller.getStorePhoto());

            sellerService.updateSeller(updateSeller);
            return ResponseHandler.generateResponse("successfully updated Seller", HttpStatus.CREATED, updateSeller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/sellers/delete/{id}")
    public ResponseEntity<Object> deleteSeller(@PathVariable("id") Long id){
        try{
            sellerService.deleteSeller(id);
            return ResponseHandler.generateResponse("successfully deleted seller", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }
}

