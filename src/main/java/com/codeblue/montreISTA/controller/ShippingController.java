package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.ShippingRequestDTO;
import com.codeblue.montreISTA.DTO.ShippingResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ShippingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name = "11. Shipping")
@SecurityRequirement(name = "bearer-key")
public class ShippingController {

    @Autowired
    ShippingService shippingService;
    private static final Logger logger = LoggerFactory.getLogger(ShippingController.class);
    private static final String Line = "====================";

    //GET ALL
    @GetMapping("/shipping")
    public ResponseEntity<Object> getAllShipping(){
        try{
            List<ShippingResponseDTO> shippings = shippingService.findAllShipping();
            logger.info("==================== Logger Start Get All Shipping    ====================");
            for(ShippingResponseDTO shippingData : shippings){
                logger.info("-------------------------");
                logger.info("Shipping ID  : " + shippingData.getShipping_id());
                logger.info("Name         : " + shippingData.getName());
                logger.info("Price        : " + shippingData.getPrice());
            }
            logger.info("==================== Logger End Get All Shipping    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved shippings", HttpStatus.OK, shippings);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Shippings    ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Table has no value")));
            logger.info("==================== Logger End Get All Shippings     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table has no value");
        }
    }


    @GetMapping("/shipping/name")
    public ResponseEntity<Object> findByName(@Param("keyword") String keyword){
        try{
            List<ShippingResponseDTO> shippings = shippingService.findByName(keyword);
            logger.info(Line + " Logger Start Get by Shipping Name " + Line);
            logger.info("Update Shipping : " + shippings);
            logger.info(Line + " Logger End Get  by Shipping Name " + Line);
            return ResponseHandler.generateResponse("successfully get name", HttpStatus.OK, shippings);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not Found!");
        }
    }

    //GET ONE BY ID
    @GetMapping("/shipping/{id}")
    public ResponseEntity<Object> getShippingById(@PathVariable("id") Long id){
        try{
            ShippingResponseDTO shipping = shippingService.findShippingById(id);
            logger.info(Line + " Logger Start Get By id Shipping " + Line);
            logger.info("Update Shipping : " + shipping);
            logger.info(Line + " Logger End Get By id Shipping " + Line);
            return ResponseHandler.generateResponse("success get by id", HttpStatus.OK, shipping);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found!");
        }
    }

    //CREATE
    @PostMapping("/dashboard/shipping/create")
    public ResponseEntity<Object> createShipping(@RequestBody ShippingRequestDTO shippingRequestDTO){
        try {
            ShippingResponseDTO shipping = shippingService.createShipping(shippingRequestDTO);
            logger.info(Line + " Logger Start Create Shipping " + Line);
            logger.info("Create Shipping : " + shipping);
            logger.info(Line + " Logger End Create Shipping " + Line);
            return ResponseHandler.generateResponse("successfully create shipping", HttpStatus.CREATED, shipping);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed create shipping!");
        }
    }

    //UPDATE
    @PutMapping("/dashboard/shipping/update/{id}")
    public ResponseEntity<Object> updateShipping(@RequestBody ShippingRequestDTO shippingRequestDTO, @PathVariable("id") Long id){
        try{
            ShippingResponseDTO result = shippingService.updateShipping(shippingRequestDTO, id);
            logger.info(Line + " Logger Start Update Shipping " + Line);
            logger.info("Update Shipping : " + result);
            logger.info(Line + " Logger End Update Shipping " + Line);
            return ResponseHandler.generateResponse("successfully updated Shipping", HttpStatus.CREATED, result);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not Found!");
        }
    }

    //DELETE
    @DeleteMapping("/dashboard/shipping/delete/{id}")
    public ResponseEntity<Object> deleteShipping(@PathVariable("id") Long id){
        try{
            shippingService.deleteShipping(id);
            logger.info(Line + " Logger Start Delete Shipping " + Line);
            logger.info("Success Delete Shipping by ID");
            logger.info(Line + " Logger End Delete Shipping " + Line);
            return ResponseHandler.generateResponse("successfully deleted shipping by id", HttpStatus.OK, "success delete");
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found!");
        }

    }

}
