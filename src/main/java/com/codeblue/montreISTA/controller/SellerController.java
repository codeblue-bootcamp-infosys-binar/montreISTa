package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.implement.SellerServiceImpl;
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
@Tag(name="02. Seller")
public class SellerController {

    private static final Logger logger =  LoggerFactory.getLogger(SellerController.class);

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    UserRepository userRepository;

    //GET ALL
    @GetMapping("/sellers")
    public ResponseEntity<Object> getAllSeller(){
        try{
            List<Seller> sellers = sellerService.findAllSeller();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Sellers     ====================");
            for(Seller sellerData : sellers){
                Map<String, Object> seller = new HashMap<>();
//                logger.info("-------------------------");
//                logger.info("Seller ID       : " + userData.getUserId());
//                logger.info("Store Address : " + userData.getUserName());
//                logger.info("Store Name    : " + userData.getEmailId());
//                logger.info("Password : " + userData.getPassword());
//                seller.put("ID            ", userData.getUserId());
//                seller.put("Username      ", userData.getUserName());
//                seller.put("Email         ", userData.getEmailId());
//                maps.add(seller);
            }
            logger.info("==================== Logger End Get All Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved sellers", HttpStatus.OK, sellers);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Users     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Table has no value")));
            logger.info("==================== Logger End Get All Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/sellers/Username")
    public ResponseEntity<Object> findByUsername(@Param("keyword") String keyword){
        try{
            List<Seller> sellers = sellerService.findByUsername(keyword);

            return ResponseHandler.generateResponse("successfully retrieved username", HttpStatus.OK, sellers);
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
    public ResponseEntity<Object> createSeller(@RequestBody SellerRequestDTO sellerRequestDTO){
        try {

            Seller seller = sellerService.createSeller(sellerRequestDTO);
            return ResponseHandler.generateResponse("successfully retrieved seller", HttpStatus.CREATED, seller);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/sellers/update/{id}")
    public ResponseEntity<Object> updateSeller(@RequestBody SellerRequestDTO sellerRequestDTO, @PathVariable("id") Long id){
        try{
            Optional<Seller> targetSeller = sellerService.findSellerById(id);
            Seller updateSeller = targetSeller.get();
            updateSeller.setSellerId(id);
            updateSeller.setStoreName(sellerRequestDTO.getStoreName());
            updateSeller.setStoreAddress(sellerRequestDTO.getStoreAddress());
            updateSeller.setStorePhoto(sellerRequestDTO.getStorePhoto());

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
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            logger.info("==================== Logger Start Delete Sellers ====================");
            logger.info("Seller Data Successfully Deleted! :" + response.put("deleted", Boolean.TRUE));
            logger.info("==================== Logger End Delete Sellers   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully deleted seller!", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            logger.info("==================== Logger Start Delete Sellers     ====================");
            logger.error(e.getMessage());
            logger.info("==================== Logger End Delete Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Data not found!");
        }

    }
}

