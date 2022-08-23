package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.BuyerService;
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
import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="05. Buyer")
@SecurityRequirement(name = "bearer-key")
public class BuyerController {

    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);
    private static final String Line = "====================";
    private final BuyerService buyerService;

    //CREATE
    @GetMapping("/user/buyers/login-as-buyer")
    public ResponseEntity<Object> createBuyer(Authentication authentication){
        try {
            List<ProductResponseDTO> results = buyerService.createBuyer(authentication);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully login as buyer", HttpStatus.CREATED, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed create buyer!");
        }
    }

    //GET ALL
    @GetMapping("/dashboard/buyer")
    public ResponseEntity<Object> getAllBuyer(){
        try{
            List<BuyerResponseDTO> buyers = buyerService.findAllBuyer();
            logger.info("==================== Logger Start Get All Buyers     ====================");
            for(BuyerResponseDTO buyerData : buyers){
                Map<String, Object> buyer = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Buyer ID    : " + buyerData.getBuyer_id());
                logger.info("User ID     : " + buyerData.getUser_id());
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

    @GetMapping("/dashboard/buyer/find-by-username")
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

    //DELETE
    @DeleteMapping("/user/buyers/delete/{id}")
    public ResponseEntity<Object> deleteBuyer(@PathVariable("id") Long id, Authentication authentication){
        try{
            buyerService.deleteBuyer(id,authentication);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted buyer", HttpStatus.OK, "Success delete buyer");
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete buyer!");
        }

    }
}

