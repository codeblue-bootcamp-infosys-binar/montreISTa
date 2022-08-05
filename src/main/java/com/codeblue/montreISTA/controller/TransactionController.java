package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.Transaction;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<Object> findAll(){
        try{
            List<Transaction> results = transactionService.findAll();
            return ResponseHandler.generateResponse("successfully retrieved transactions", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * find By Buyer
     * @param keyword
     * @return
     */
    @GetMapping("/transaction/buyer")
    public ResponseEntity<Object> findByBuyer(@Param("keyword") String keyword){
        try{
            List<Transaction> results = transactionService.findByBuyer(keyword);
            return ResponseHandler.generateResponse("successfully find transaction", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * find By seller
     * @param keyword
     * @return
     */
    @GetMapping("/transaction/seller")
    public ResponseEntity<Object> findBySeller(@Param("keyword") String keyword){
        try{
            List<Transaction> results = transactionService.findBySeller(keyword);
            return ResponseHandler.generateResponse("successfully find transaction", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> postTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction results = transactionService.createTransaction(transaction);
            return ResponseHandler.generateResponse("successfully create transaction", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<Object> updateCart(@PathVariable Long id, @RequestBody Transaction transaction) {
        try {
            Transaction results = transactionService.updateTransaction(transaction,id);
            return ResponseHandler.generateResponse("successfully update transaction", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<Object> deleteCart(@PathVariable Long id) {
        try {
            transactionService.deleteById(id);
            return ResponseHandler.generateResponse("successfully delete transactions", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
