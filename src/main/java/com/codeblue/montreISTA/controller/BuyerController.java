package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.BuyerRequestDTO;
import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.BuyerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="06. Buyer")
public class BuyerController {

    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);

    private static final String Line = "====================";

    private BuyerService buyerService;

    //GET ALL
    @GetMapping("/user/buyer")
    public ResponseEntity<Object> getAllBuyer(){
        try{
            List<BuyerResponseDTO> buyers = buyerService.findAllBuyer();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Buyers     ====================");
            for(BuyerResponseDTO buyerData : buyers){
                Map<String, Object> buyer = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Buyer ID    : " + buyerData.getBuyer_id());
                logger.info("User ID     : " + buyerData.getUser_id());
                buyer.put("Buyer ID        ", buyerData.getBuyer_id());
                buyer.put("User ID         ", buyerData.getBuyer_id());
                maps.add(buyer);
            }
            logger.info("==================== Logger End Get All Buyers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully get all buyers", HttpStatus.OK, buyers);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Buyers    ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Buyer had no value!")));
            logger.info("==================== Logger End Get All Buyers    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Buyer had no value!");
        }
    }

    @GetMapping("/buyers/store")
    public ResponseEntity<Object> findByUsername(@Param ("keyword")String keyword){
        try{
            List<BuyerResponseDTO> buyers = buyerService.findByUsername(keyword);
            logger.info(Line + "Logger Start Get buyer username " + Line);
            logger.info(String.valueOf(buyers));
            logger.info(Line + "Logger End Get buyer username " + Line);
            return ResponseHandler.generateResponse("successfully buyer username", HttpStatus.OK, buyers);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Buyer no value!");
        }
    }


    //GET ONE BY ID
    @GetMapping("/buyers/{id}")
    public ResponseEntity<Object> getBuyerById(@PathVariable("id") Long id){
        try{
            BuyerResponseDTO buyer = buyerService.findBuyerById(id);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(buyer));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("success get by id", HttpStatus.OK, buyer);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Buyer no value!");
        }
    }

    //CREATE
    @GetMapping("/user/buyers/loginAsBuyer")
    public ResponseEntity<Object> createBuyer(Authentication authentication){
        try {
            BuyerResponseDTO buyer = buyerService.createBuyer(authentication);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(buyer));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create buyer", HttpStatus.CREATED, buyer);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed create buyer!");
        }
    }

    //UPDATE
    @PutMapping("/user/buyers/update/{id}")
    public ResponseEntity<Object> updateBuyer(@RequestBody BuyerRequestDTO buyer, @PathVariable("id") Long id){
        try{
            BuyerResponseDTO updateBuyer = buyerService.updateBuyer(buyer,id);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(updateBuyer));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated Buyer", HttpStatus.OK, updateBuyer);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update buyer!");
        }
    }

    //DELETE
    @DeleteMapping("/user/buyers/delete/{id}")
    public ResponseEntity<Object> deleteBuyer(@PathVariable("id") Long id){
        try{
            buyerService.deleteBuyer(id);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted buyer", HttpStatus.OK, null);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete buyer!");
        }

    }
}

