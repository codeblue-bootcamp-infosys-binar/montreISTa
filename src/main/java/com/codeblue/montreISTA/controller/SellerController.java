package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.SellerServiceImpl;
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
public class SellerController {

    private static final Logger logger =  LoggerFactory.getLogger(SellerController.class);
    @Autowired
    SellerServiceImpl sellerServiceImpl;

    @Autowired
    UserRepository userRepository;

    //GET ALL
    @GetMapping("/sellers")
    public ResponseEntity<Object> getAllSeller(){
        try{
            List<Seller> sellers = sellerServiceImpl.findAllSeller();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Sellers     ====================");
            for(Seller sellerData : sellers){
                Map<String, Object> user = new HashMap<>();

                logger.info("-------------------------");
                logger.info("Seller ID       : " + sellerData.getSellerId());
                logger.info("Store Address   : " + sellerData.getStoreAddress());
                logger.info("Store Name      : " + sellerData.getStoreName());
                logger.info("Store Photo     : " + sellerData.getStorePhoto());
                user.put("Seller ID          ", sellerData.getSellerId());
                user.put("Store Address      ", sellerData.getStoreAddress());
                user.put("Store Name         ", sellerData.getStoreName());
                user.put("Store Photo        ", sellerData.getStorePhoto());
                maps.add(user);
            }
            logger.info("==================== Logger End Get All Sellers     ====================");
            logger.info(" ");
                return ResponseHandler.generateResponse("successfully get all sellers", HttpStatus.OK, sellers);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Sellers     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Table has no value!")));
            logger.info("==================== Logger End Get All Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table has no value!");
        }
    }

    @GetMapping("/sellers/Username")
    public ResponseEntity<Object> findByUsername(@Param("keyword") String keyword){
        try{
            List<Seller> sellers = sellerServiceImpl.findByUsername(keyword);
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get  Sellers by Username     ====================");
            for(Seller sellerData : sellers){
                Map<String, Object> user = new HashMap<>();

                logger.info("-------------------------");
                logger.info("Seller ID       : " + sellerData.getSellerId());
                logger.info("Store Address   : " + sellerData.getStoreAddress());
                logger.info("Store Name      : " + sellerData.getStoreName());
                logger.info("Store Photo     : " + sellerData.getStorePhoto());
                user.put("Seller ID          ", sellerData.getSellerId());
                user.put("Store Address      ", sellerData.getStoreAddress());
                user.put("Store Name         ", sellerData.getStoreName());
                user.put("Store Photo        ", sellerData.getStorePhoto());
                maps.add(user);
            }
            logger.info("==================== Logger End Get All Sellers by Username    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved username", HttpStatus.OK, sellers);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Sellers by Username     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Table has no value!")));
            logger.info("==================== Logger End Get All Sellers by Username     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table has no value!");
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

            Seller sellerResult = sellerServiceImpl.createSeller(sellerRequestDTO);
            Map<String, Object> sellerMap = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();

            logger.info("==================== Logger Start Create Sellers ====================");
            logger.info("Seller Successfully Created !");
            logger.info("Seller ID       : " + sellerResult.getSellerId());
            logger.info("Store Address   : " + sellerResult.getStoreAddress());
            logger.info("Store Name      : " + sellerResult.getStoreName());
            logger.info("Store Photo     : " + sellerResult.getStorePhoto());

            sellerMap.put("Seller ID          ", sellerResult.getSellerId());
            sellerMap.put("Store Address      ", sellerResult.getStoreAddress());
            sellerMap.put("Store Name         ", sellerResult.getStoreName());
            sellerMap.put("Store Photo        ", sellerResult.getStorePhoto());
            maps.add(sellerMap);
            logger.info("==================== Logger End Create Sellers   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully created seller", HttpStatus.CREATED, sellerResult);
        } catch (Exception e){
            logger.info("==================== Logger Start Create Sellers     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Seller Already Exist!")));
            logger.info("==================== Logger End Create Sellers    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Seller Already Exist!");
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

