package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.TransactionDetailDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.entity.HistoryTransaction;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="9. Transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/Transactions")
    public ResponseEntity<Object> findAllHistoryTransaction(){
        try{
            List<TransactionResponseDTO> results = transactionService.findAllTransaction();
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/Transactions/{id}")
    public ResponseEntity<Object> findAllHistoryTransactionId(@PathVariable Long id){
        try{
            TransactionResponseDTO results = transactionService.findByTransactionId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/Transactions/buyer/{id}")
    public ResponseEntity<Object> findTransactionByBuyer(@PathVariable Long id){
        try{
            List<TransactionResponseDTO> results = transactionService.findByTransactionBuyerId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/Transactions/seller/{id}")
    public ResponseEntity<Object> findTransactionBySeller(@PathVariable Long id){
        try{
            List<TransactionResponseDTO> results = transactionService.findByTransactionSellerId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/TransactionDetails")
    public ResponseEntity<Object> findAllTransactionDetails(){
        try{
            List<TransactionDetailDTO> results = transactionService.findAllTransactionDetail();
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/TransactionDetails/{id}")
    public ResponseEntity<Object> findTransactionDetailsId(@PathVariable Long id){
        try{
            TransactionDetailDTO results = transactionService.findByTransactionDetailId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/TransactionsDetail/buyer/{id}")
    public ResponseEntity<Object> findTransactionDetailsByBuyer(@PathVariable Long id){
        try{
            List<TransactionDetailDTO> results = transactionService.findByTransactionDetailBuyerId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/TransactionsDetail/seller/{id}")
    public ResponseEntity<Object> findTransactionDetailBySeller(@PathVariable Long id){
        try{
            List<TransactionDetailDTO> results = transactionService.findByTransactionDetailSellerId(id);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/FinishOrder/{id}")
    public ResponseEntity<Object> postCart(@PathVariable Long id) {
        try {
            String results = transactionService.createTransaction(id);
            return ResponseHandler.generateResponse("Successfully Transaction", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Doing Transactions");
        }
    }
}
