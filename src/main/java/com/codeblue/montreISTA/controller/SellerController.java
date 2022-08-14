package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.SellerRequestDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.SellerService;
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
@Tag(name="02. Seller")
public class SellerController {

    private static final Logger logger =  LoggerFactory.getLogger(SellerController.class);
    private static final String Line = "====================";
    private final SellerService sellerService;
    private final UserRepository userRepository;

    //CREATE
    @PostMapping("/user/sellers/loginAsSeller")
    public ResponseEntity<Object> createSeller(@RequestBody SellerRequestDTO sellerRequestDTO, Authentication authentication){
        try {
            SellerResponseDTO seller = sellerService.createSeller(sellerRequestDTO,authentication);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(seller));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully login as seller", HttpStatus.CREATED, seller);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed create seller!");
        }
    }

    //UPDATE
    @PutMapping("/user/sellers/EditStoreProfile")
    public ResponseEntity<Object> updateSeller(@RequestBody SellerRequestDTO sellerRequestDTO, Authentication authentication){
        try{
            SellerResponseDTO updateSeller = sellerService.updateSeller(sellerRequestDTO,authentication);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(updateSeller));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated Buyer", HttpStatus.OK, updateSeller);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update seller!");
        }
    }

    //GET ALL
    @GetMapping("/dashboard/sellers")
    public ResponseEntity<Object> getAllSeller(){
        try{
            List<SellerResponseDTO> sellers = sellerService.findAllSeller();
            logger.info("==================== Logger Start Get All Sellers     ====================");
            for(SellerResponseDTO sellerData : sellers){
                logger.info("-------------------------");
                logger.info("Seller ID       : " + sellerData.getSellerId());
                logger.info("Store Address   : " + sellerData.getStore_address());
                logger.info("Store Name      : " + sellerData.getStore_name());
                logger.info("Store Photo     : " + sellerData.getStore_photo());
            }
            logger.info("==================== Logger End Get All Sellers    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully get all sellers", HttpStatus.OK, sellers);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Sellers     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Seller had no value!")));
            logger.info("==================== Logger End Get All Sellers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Seller had no value!");
        }
    }

    @GetMapping("/dashboard/sellers/username")
    public ResponseEntity<Object> findByUsername(@Param("keyword") String keyword){
        try{
            List<SellerResponseDTO> sellers = sellerService.findByUsername(keyword);
            logger.info(Line + "Logger Start Get seller username " + Line);
            logger.info(String.valueOf(sellers));
            logger.info(Line + "Logger End Get seller username " + Line);
            return ResponseHandler.generateResponse("success get seller username", HttpStatus.OK, sellers);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "seller had no value!");
        }
    }

    //DELETE
    @DeleteMapping("/dashboard/sellers/delete/{id}")
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

