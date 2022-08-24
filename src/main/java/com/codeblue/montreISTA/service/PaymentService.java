package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.DTO.PaymentRequestDTO;
import com.codeblue.montreISTA.DTO.PaymentResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PaymentService {

    ResponseEntity<Object> findAllPayment() throws Exception;
    ResponseEntity<Object> findPaymentById(Long id)throws Exception;
    ResponseEntity<Object> findByPaymentName(String keyword)throws Exception;
    ResponseEntity<Object> addPayment(PaymentRequestDTO paymentRequestDTO) throws Exception;
    ResponseEntity<Object> updatePayment(PaymentRequestDTO paymentRequestDTO, Long id) throws Exception;
    ResponseEntity<Object> deletePayment(Long paymentId) throws Exception;

}





