package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PaymentService;
import com.codeblue.montreISTA.entity.Payment;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/codeBlue")
public class PaymentController {

    private PaymentService paymentService;


    //CREATE
    @PostMapping("/payment/create")
    public ResponseEntity<Object> addPayment(@RequestBody Payment newPayment) {
        try {
            Payment payment = paymentService.addPayment(newPayment);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.CREATED, payment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //READ
    @GetMapping("/payment")
    public ResponseEntity<Object> getAllPayment(){
        try{
            List<Payment> payments = paymentService.findAllPayment();

            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, payments);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/payment/paymentName")
    public ResponseEntity<Object> findByPaymentName(@Param("keyword") String keyword){
        try{
            List<Payment> payments = paymentService.findByPaymentName(keyword);

            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, payments);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //READ BY ID
    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable("id") Long id) {
        try{
            Optional<Payment> payment = paymentService.findPaymentById(id);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, payment);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //UPDATE
    @PutMapping("/payment/update/{id}")
    public ResponseEntity<Object> updatePayment(@RequestBody Payment payment, @PathVariable("id") Long id) {
        try {
            Optional<Payment> targetProduct = paymentService.findPaymentById(id);
            Payment updatePayment = targetProduct.get();
            updatePayment.setPaymentId(id);
            updatePayment.setName(payment.getName());
            updatePayment.setPaymentCode(payment.getPaymentCode());

            paymentService.updatePayment(updatePayment);
            return ResponseHandler.generateResponse("successfully updated payment", HttpStatus.CREATED, updatePayment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    //DELETE
    @DeleteMapping("/payment/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable("id") Long id) {
    try{
        paymentService.deletePayment(id);
        return ResponseHandler.generateResponse("successfully deleted product", HttpStatus.MULTI_STATUS, null);
    } catch(Exception e){
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }

    }


    //READ BY NAME
//    @GetMapping("/payment/:name")
//    public ResponseEntity<Object> getPaymentByName(String name) {
//        List<Payment> payment = paymentServiceImpl.findPaymentByName(name);
//        List<PaymentResponseDTO> result = new ArrayList<>();
//
//        for (Payment data : payment) {
//            PaymentResponseDTO paymentResponseDTO = data.convertToResponse();
//            result.add(paymentResponseDTO);
//        }
//
//        return ResponseHandler.generateResponse("Succes All", HttpStatus.OK, result);
//    }

}

