package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.SellerServiceImpl;
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
@Tag(name="2. Seller")
public class SellerController {

    private static final Logger logger =  LoggerFactory.getLogger(SellerController.class);

    private static final String Line = "====================";

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
                logger.info("-------------------------");
                logger.info("Seller ID       : " + sellerData.getSellerId());
                logger.info("Store Address   : " + sellerData.getStoreAddress());
                logger.info("Store Name      : " + sellerData.getStoreName());
                logger.info("Store Photo     : " + sellerData.getStorePhoto());
                seller.put("Seller ID          ", sellerData.getSellerId());
                seller.put("Store Address      ", sellerData.getStoreAddress());
                seller.put("Store Name         ", sellerData.getStoreName());
                seller.put("Store Photo        ", sellerData.getStorePhoto());
                maps.add(seller);
            }
            logger.info("==================== Logger End Get All Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved sellers", HttpStatus.OK, sellers);
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
            List<Seller> sellers = sellerService.findByUsername(keyword);
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Sellers by Username     ====================");
            for(Seller sellerData : sellers){
                Map<String, Object> seller = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Seller ID       : " + sellerData.getSellerId());
                logger.info("Store Address   : " + sellerData.getStoreAddress());
                logger.info("Store Name      : " + sellerData.getStoreName());
                logger.info("Store Photo     : " + sellerData.getStorePhoto());
                seller.put("Seller ID          ", sellerData.getSellerId());
                seller.put("Store Address      ", sellerData.getStoreAddress());
                seller.put("Store Name         ", sellerData.getStoreName());
                seller.put("Store Photo        ", sellerData.getStorePhoto());
                maps.add(seller);
            }
            logger.info("==================== Logger End Get All Sellers by Username     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved username", HttpStatus.OK, sellers);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Sellers by Username    ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Data not Found!")));
            logger.info("==================== Logger End Get All Sellers Username     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!");
        }
    }


    //GET ALL BY SELLER ID
    @GetMapping("/sellers/store/{seller_id}")
    public ResponseEntity<Object> getAllSellerBySellerId(@PathVariable("seller_id") Long sellerId){
        try{
            List<Seller> seller = sellerService.findSellertBySellerId(sellerId);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(seller));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully get by seller id", HttpStatus.OK, seller);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Seller no value");
        }
    }

    //GET ONE BY ID
    @GetMapping("/sellers/{id}")
    public ResponseEntity<Object> getSellerById(@PathVariable("id") Long id){
        try{
           Optional<Seller> seller = sellerService.findSellerById(id);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(seller));
            logger.info(Line + "Logger End Get By Id " + Line);
//            List<Map<String, Object>> maps = new ArrayList<>();
//            for(Seller sellerData : seller) {
//                logger.info("==================== Logger Start Find By ID Users ====================");
//                logger.info("ID       : " + sellerData.getSellerId());
//                logger.info("Username : " + userResult.getUserName());
//                logger.info("Email    : " + userResult.getEmailId());
//                logger.info("==================== Logger End Find By ID Users   ====================");
//                logger.info(" ");
//            }
            return ResponseHandler.generateResponse("successfully get by id", HttpStatus.OK, seller);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Seller no value");
        }
    }

    //CREATE
    @PostMapping("/sellers/create")
    public ResponseEntity<Object> createSeller(@RequestBody SellerRequestDTO sellerRequestDTO){
        try {
            Seller seller = sellerService.createSeller(sellerRequestDTO);
            Map<String, Object> sellerMap = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();

            logger.info("==================== Logger Start Create Sellers ====================");
            logger.info("Seller Successfully Created !");
            logger.info("Seller ID       : " + seller.getSellerId());
            logger.info("Store Address   : " + seller.getStoreAddress());
            logger.info("Store Name      : " + seller.getStoreName());
            logger.info("Store Photo     : " + seller.getStorePhoto());


            sellerMap.put("Seller ID            ", seller.getSellerId());
            sellerMap.put("Store Address        ", seller.getStoreAddress());
            sellerMap.put("Store Name           ", seller.getStoreName());
            sellerMap.put("Store Photo          ",seller.getStorePhoto());
            maps.add(sellerMap);
            logger.info("==================== Logger End Create Sellers   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully created seller", HttpStatus.CREATED, seller);
        } catch (Exception e){
            logger.info("==================== Logger Start Create Sellers     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"User Already Exist!")));
            logger.info("==================== Logger End Create Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Seller Already Exist!");
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
            logger.info("==================== Logger Start Update Seller ====================");
            logger.info("Seller Data Successfully Updated !");
            logger.info("Seller ID       : " + updateSeller.getSellerId());
            logger.info("Store Address   : " + updateSeller.getStoreAddress());
            logger.info("Store Name      : " + updateSeller.getStoreName());
            logger.info("Store Photo     : " + updateSeller.getStorePhoto());
            logger.info("==================== Logger End Update Sellers   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully updated Seller", HttpStatus.OK, updateSeller);
        } catch (Exception e){
            logger.info("==================== Logger Start Update Sellers     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Data Not Found!")));
            logger.info("==================== Logger End Update Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!");
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
            return ResponseHandler.generateResponse("successfully deleted seller!", HttpStatus.OK, response);
        } catch (Exception e){
            logger.info("==================== Logger Start Delete Sellers     ====================");
            logger.error(e.getMessage());
            logger.info("==================== Logger End Delete Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found!");
        }

    }
}

