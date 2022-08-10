package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.entity.Payment;
import com.codeblue.montreISTA.repository.PaymentRepository;
import com.codeblue.montreISTA.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAllPayment() {
        List<Payment> payments = paymentRepository.findAll();
            return payments;
    }

    @Override
    public Optional<Payment> findPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> findByPaymentName(String keyword) {
        List<Payment> paymentName = paymentRepository.findByNameContaining(keyword);
        return paymentName;
    }

    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment, Long id) {
        Optional<Payment> targetProduct = paymentRepository.findById(id);
        Payment updatePayment = targetProduct.get();
        updatePayment.setPaymentId(id);
        updatePayment.setName(payment.getName());
        updatePayment.setPaymentCode(payment.getPaymentCode());

        return paymentRepository.save(updatePayment);
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }

}