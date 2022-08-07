package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.entity.Payment;

import java.util.List;
import java.util.Optional;


public interface PaymentService {

    List<Payment> findAllPayment();
    Optional<Payment> findPaymentById(Long id);
    List<Payment> findByPaymentName(String keyword);
    Payment addPayment(Payment payment);
    Payment updatePayment(Payment payment);
    void deletePayment(Long paymentId);

}





