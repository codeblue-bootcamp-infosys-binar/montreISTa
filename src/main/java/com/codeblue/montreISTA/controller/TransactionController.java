package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name="09. Transaction")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private static final String Line = "====================";
    private final TransactionService transactionService;

    @GetMapping("/dashboard/transactions")
    public ResponseEntity<Object> findAllHistoryTransaction(){
        try{
            List<TransactionResponseDTO> transactions = transactionService.findAllTransaction();
            List<TransactionResponseDTO> results = transactionService.findAllTransaction();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Transactions     ====================");
            for(TransactionResponseDTO transactionData : transactions){
                Map<String, Object> transaction = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Transactions ID  : " + transactionData.getTransaction_id());
                logger.info("Store Name       : " + transactionData.getStore_name());
                logger.info("Product Name     : " + transactionData.getProduct_name());
                logger.info("Product Price    : " + transactionData.getProduct_price());
                logger.info("Photo url        : " + transactionData.getPhoto_url());
                logger.info("Buyer ID         : " + transactionData.getBuyer_id());
                logger.info("Seller ID        : " + transactionData.getSeller_id());
                logger.info("Quantity         : " + transactionData.getQuantity());
                logger.info("Total Price      : " + transactionData.getTotal_price());
                transaction.put("Transactions ID  : ",  transactionData.getTransaction_id());
                transaction.put("Store Name       : ",  transactionData.getStore_name());
                transaction.put("Product Name     : ", transactionData.getProduct_name());
                transaction.put("Product Price    : ", transactionData.getProduct_price());
                transaction.put("Photo url        : ", transactionData.getPhoto_url());
                transaction.put("Buyer ID         : ", transactionData.getBuyer_id());
                transaction.put("Seller ID        : ", transactionData.getSeller_id());
                transaction.put("Quantity         : ", transactionData.getQuantity());
                transaction.put("Total Price      : ", transactionData.getTotal_price());
                maps.add(transaction);
            }
            logger.info("==================== Logger End Get AlL Transactions   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions had no value!");
        }
    }

    @GetMapping("/dashboard/transactions/{id}")
    public ResponseEntity<Object> findByHistoryTransactionId(@PathVariable Long id)throws Exception{
        try{
            TransactionResponseDTO results = transactionService.findByTransactionId(id);
            logger.info(Line + "Logger Start Get History Transactions " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get History Transactions " + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "History transactions doesn't exist!");
        }
    }



    @GetMapping("/user/transactions/buyer")
    public ResponseEntity<Object> findTransactionByBuyer(Authentication authentication)throws Exception{
        try{
            List<TransactionResponseDTO> results = transactionService.findByTransactionBuyerId(authentication);
            logger.info(Line + "Logger Start Get Transactions Buyer" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transactions Buyer" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/user/transactions/seller")
    public ResponseEntity<Object> findTransactionBySeller(Authentication authentication)throws Exception{
        try{
            List<TransactionResponseDTO> results = transactionService.findByTransactionSellerId(authentication);
            logger.info(Line + "Logger Start Get Transaction By Seller" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transaction By Seller" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @GetMapping("/dashboard/transaction-details")
    public ResponseEntity<Object> findAllTransactionDetails(){
        try{
            List<TransactionDetailResponseDTO> results = transactionService.findAllTransactionDetail();
            logger.info(Line + "Logger Start Get Transaction Detail" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transaction Detail" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @GetMapping("/dashboard/transaction-details/{id}")
    public ResponseEntity<Object> findTransactionDetailsId(@PathVariable Long id){
        try{
            TransactionDetailResponseDTO results = transactionService.findByTransactionDetailId(id);
            logger.info(Line + "Logger Start Get By ID" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By ID" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @GetMapping("/user/transactions-detail/buyer")
    public ResponseEntity<Object> findTransactionDetailsByBuyer(Authentication authentication)throws Exception{
        try{
            List<TransactionDetailResponseDTO> results = transactionService.findByTransactionDetailBuyerId(authentication);
            logger.info(Line + "Logger Start Get Transactions Detail Buyer" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transactions Detail Buyer" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @GetMapping("/user/transactions-detail/seller")
    public ResponseEntity<Object> findTransactionDetailBySeller(Authentication authentication)throws Exception{
        try{
            List<TransactionDetailResponseDTO> results = transactionService.findByTransactionDetailSellerId(authentication);
            logger.info(Line + "Logger Start Get Transactions Detail Seller" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transactions Detail Seller" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @GetMapping("/user/finish-order")
    public ResponseEntity<Object> postCart(Authentication authentication) throws Exception{
        try {
            List<TransactionDetailResponseDTO> results = transactionService.createTransaction(authentication);
            logger.info(Line + "Logger Start Get Finish Order" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Finish Order " + Line);
            return ResponseHandler.generateResponse("Successfully Transaction", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Doing Transactions");
        }
    }
}
