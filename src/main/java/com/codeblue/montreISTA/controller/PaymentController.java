package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.PaymentRequestDTO;
import com.codeblue.montreISTA.DTO.PaymentResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private static final String Line = "====================";

    private PaymentService paymentService;


    /**
     * Create Payment
     *
     */
    @PostMapping("/dashboard/payment/create")
    public ResponseEntity<Object> addPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) throws Exception {
          return paymentService.addPayment(paymentRequestDTO);
    }

    /**
     * Find All Payment
     * @return
     */
    @GetMapping("/payment")
    public ResponseEntity<Object> getAllPayment() throws Exception {
            return paymentService.findAllPayment();
    }

    /**
     * find By paymentName
     * @param keyword
     * @return
     */
    @GetMapping("/payment/paymentName")
    public ResponseEntity<Object> findByPaymentName(@Param("keyword") String keyword) throws Exception {
            return paymentService.findByPaymentName(keyword);
    }

    /**
     * Find By Id
     * @return
     */
    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable("id") Long id) throws Exception {
            return paymentService.findPaymentById(id);

    }

    /**
     * Update Payment
     * @param id
     * @param
     * @return
     */
    @PutMapping("/dashboard/payment/update/{id}")
    public ResponseEntity<Object> updatePayment(@RequestBody PaymentRequestDTO PaymentResponseDTO, @PathVariable("id") Long id) throws Exception {
            return paymentService.updatePayment(PaymentResponseDTO,id);
    }


    /**
     * Delete Payment
     * @param id
     * @return
     */
    @DeleteMapping("/dashboard/payment/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable("id") Long id) throws Exception {
       return paymentService.deletePayment(id);
    }

}

