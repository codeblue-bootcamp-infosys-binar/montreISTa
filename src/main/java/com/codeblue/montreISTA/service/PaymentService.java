package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.DTO.PaymentRequestDTO;
import com.codeblue.montreISTA.DTO.PaymentResponseDTO;
import java.util.List;


public interface PaymentService {

    List<PaymentResponseDTO> findAllPayment() throws Exception;
    PaymentResponseDTO findPaymentById(Long id)throws Exception;
    List<PaymentResponseDTO> findByPaymentName(String keyword)throws Exception;
    PaymentResponseDTO addPayment(PaymentRequestDTO paymentRequestDTO) throws Exception;
    PaymentResponseDTO updatePayment(PaymentRequestDTO paymentRequestDTO, Long id) throws Exception;
    void deletePayment(Long paymentId) throws Exception;

}





