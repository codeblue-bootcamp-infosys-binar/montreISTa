package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="10. Transaction")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/dashboard/transactions")
    public ResponseEntity<Object> findAllHistoryTransaction(){
        try{
            List<TransactionResponseDTO> results = transactionService.findAllTransaction();
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/dashboard/transactions/{id}")
    public ResponseEntity<Object> findByHistoryTransactionId(@PathVariable Long id)throws Exception{
        try{
            TransactionResponseDTO results = transactionService.findByTransactionId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/user/transactions/buyer")
    public ResponseEntity<Object> findTransactionByBuyer(Authentication authentication)throws Exception{
        try{
            List<TransactionResponseDTO> results = transactionService.findByTransactionBuyerId(authentication);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/user/transactions/seller")
    public ResponseEntity<Object> findTransactionBySeller(Authentication authentication)throws Exception{
        try{
            List<TransactionResponseDTO> results = transactionService.findByTransactionSellerId(authentication);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/dashboard/transaction-details")
    public ResponseEntity<Object> findAllTransactionDetails(){
        try{
            List<TransactionDetailResponseDTO> results = transactionService.findAllTransactionDetail();
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/dashboard/transaction-details/{id}")
    public ResponseEntity<Object> findTransactionDetailsId(@PathVariable Long id){
        try{
            TransactionDetailResponseDTO results = transactionService.findByTransactionDetailId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/user/transactions-detail/buyer")
    public ResponseEntity<Object> findTransactionDetailsByBuyer(Authentication authentication)throws Exception{
        try{
            List<TransactionDetailResponseDTO> results = transactionService.findByTransactionDetailBuyerId(authentication);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/user/transactions-detail/seller/")
    public ResponseEntity<Object> findTransactionDetailBySeller(Authentication authentication)throws Exception{
        try{
            List<TransactionDetailResponseDTO> results = transactionService.findByTransactionDetailSellerId(authentication);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/user/finish-order")
    public ResponseEntity<Object> postCart(Authentication authentication) throws Exception{
        try {
            List<TransactionDetailResponseDTO> results = transactionService.createTransaction(authentication);
            return ResponseHandler.generateResponse("Successfully Transaction", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Doing Transactions");
        }
    }
}
