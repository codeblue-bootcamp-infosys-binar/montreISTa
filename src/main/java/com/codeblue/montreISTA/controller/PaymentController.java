package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PaymentService;
import com.codeblue.montreISTA.entity.Payment;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping
@Tag(name = "11. Payment")
public class PaymentController {

    private PaymentService paymentService;


    /**
     * Create Payment
     * @param newPayment
     * @return
     */
    @PostMapping("/payment/create")
    public ResponseEntity<Object> addPayment(@RequestBody Payment newPayment) {
        try {
            Payment payment = paymentService.addPayment(newPayment);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.CREATED, payment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * Find All Payment
     * @return
     */
    @GetMapping("/payment")
    public ResponseEntity<Object> getAllPayment(){
        try{
            List<Payment> payments = paymentService.findAllPayment();
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, payments);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * find By paymentName
     * @param keyword
     * @return
     */
    @GetMapping("/payment/paymentName")
    public ResponseEntity<Object> findByPaymentName(@Param("keyword") String keyword){
        try{
            List<Payment> payments = paymentService.findByPaymentName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, payments);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * Find By Id
     * @return
     */
    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable("id") Long id) {
        try{
            Optional<Payment> payment = paymentService.findPaymentById(id);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, payment);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * Update Payment
     * @param id
     * @param payment
     * @return
     */
    @PutMapping("/payment/update/{id}")
    public ResponseEntity<Object> updatePayment(@RequestBody Payment payment, @PathVariable("id") Long id) {
        try {
            Payment results = paymentService.updatePayment(payment,id);
            return ResponseHandler.generateResponse("successfully updated payment", HttpStatus.CREATED, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    /**
     * Delete Payment
     * @param id
     * @return
     */
    @DeleteMapping("/payment/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable("id") Long id) {
    try{
        paymentService.deletePayment(id);
        return ResponseHandler.generateResponse("successfully deleted payment", HttpStatus.MULTI_STATUS, null);
    } catch(Exception e){
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }

    }

}

