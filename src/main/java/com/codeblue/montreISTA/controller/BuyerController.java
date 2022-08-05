package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.BuyerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class BuyerController {

    @Autowired
    BuyerService buyerService;

    //GET ALL
    @GetMapping("/buyer")
    public ResponseEntity<Object> getAllBuyer() {
        try {
            List<Buyer> buyers = buyerService.findAllBuyer();
            return ResponseHandler.generateResponse("succesfully retrieved buyers", HttpStatus.OK, buyers);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //GET ALL BY BUYER ID
    @GetMapping("/buyers/buy/{buyer_id}")
    public ResponseEntity<Object> getAllBuyerByBuyerId(@PathVariable("buyer_id") Long buyerId){
        try {
            List<Buyer> buyer = buyerService.findBuyerByBuyerId(buyerId);
            return ResponseHandler.generateResponse("succesfully retrieved buyer", HttpStatus.OK, buyer);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //GET ONE BY ID
    @GetMapping("buyers/{id}")
    public ResponseEntity<Object> getBuyerById(@PathVariable("id") Long id) {
        try {
            Optional<Buyer> buyer = buyerService.findBuyerById(id);
            return ResponseHandler.generateResponse("Succesfully Retrieved Buyer", HttpStatus.OK, buyer);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //CREATE
    @PostMapping("/buyers/create")
    public ResponseEntity<Object> createBuyer(@RequestBody Buyer newBuyer){
        try {
            Buyer buyer = buyerService.createBuyer(newBuyer);
            return ResponseHandler.generateResponse("Succesfully Create Buyer", HttpStatus.CREATED, buyer);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/buyers/update/{id}")
    public ResponseEntity<Object> updateBuyer(@RequestBody Buyer buyer, @PathVariable("id") Long id){
        try {
            Optional<Buyer> targetBuyer = buyerService.findBuyerById(id);
            Buyer updateBuyer = targetBuyer.get();
            updateBuyer.setBuyerId(id);
            buyerService.updateBuyer(updateBuyer);
            return ResponseHandler.generateResponse("successfully updated Buyer", HttpStatus.CREATED, updateBuyer);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/buyers/delete/{id}")
    public ResponseEntity<Object> deleteBuyer(@PathVariable("id") Long id){
        try {
            buyerService.deleteBuyer(id);
            return ResponseHandler.generateResponse("Succesfully Delete Buyer",HttpStatus.MULTI_STATUS,null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }
}
