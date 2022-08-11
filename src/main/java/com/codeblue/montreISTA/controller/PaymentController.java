package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PaymentService;
import com.codeblue.montreISTA.entity.Payment;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping
@Tag(name = "12. Payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private static final String Line = "====================";

    private PaymentService paymentService;


    /**
     * Create Payment
     * @param newPayment
     * @return
     */
    @PostMapping("/dashboard/payment/create")
    public ResponseEntity<Object> addPayment(@RequestBody Payment newPayment) {
        try {
            Payment payment = paymentService.addPayment(newPayment);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(payment));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.CREATED, payment);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create payment!");
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
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Payments     ====================");
            for(Payment paymentData : payments){
                Map<String, Object> payment = new HashMap<>();

                logger.info("-------------------------");
                logger.info("Payment ID    : " + paymentData.getPaymentId());
                logger.info("Name          : "      + paymentData.getName());
                logger.info("Payment Code  : " + paymentData.getPaymentCode());
                payment.put("Payment ID      ", paymentData.getPaymentId());
                payment.put("Name            ", paymentData.getName());
                payment.put("Payment Co      ", paymentData.getPaymentCode());
                maps.add(payment);
            }
            logger.info("==================== Logger End Get All Payments     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("success get all payment", HttpStatus.OK, payments);
        } catch (Exception e){
            logger.info("==================== Logger Start Get All Payments     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Table has no value")));
            logger.info("==================== Logger End Get All Payments     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table had no value!");
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
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(payments));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, payments);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Payment had no value!");
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
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(payment));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("success get by id", HttpStatus.OK, payment);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Payment had no value!");
        }
    }

    /**
     * Update Payment
     * @param id
     * @param payment
     * @return
     */
    @PutMapping("/dashboard/payment/update/{id}")
    public ResponseEntity<Object> updatePayment(@RequestBody Payment payment, @PathVariable("id") Long id) {
        try {
            Payment results = paymentService.updatePayment(payment,id);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated payment", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update payment!");
        }
    }


    /**
     * Delete Payment
     * @param id
     * @return
     */
    @DeleteMapping("/dashboard/payment/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable("id") Long id) {
    try{
        paymentService.deletePayment(id);
        logger.info(Line + "Logger Start Delete By Id " + Line);
        logger.info("Delete Success");
        logger.info(Line + "Logger End Delete By Id " + Line);
        return ResponseHandler.generateResponse("successfully deleted payment", HttpStatus.OK, null);
    } catch(Exception e){
        logger.error(Line + " Logger Start Error " + Line);
        logger.error(e.getMessage());
        logger.error(Line + " Logger End Error " + Line);
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed data payment!");
    }

    }

}

