package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.PaymentRequestDTO;
import com.codeblue.montreISTA.DTO.PaymentResponseDTO;
import com.codeblue.montreISTA.entity.Payment;
import com.codeblue.montreISTA.repository.PaymentRepository;
import com.codeblue.montreISTA.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentResponseDTO> findAllPayment() throws Exception {
        List<PaymentResponseDTO> payments = paymentRepository.findAll().stream()
                .map(Payment::convertToResponse)
                .collect(Collectors.toList());
        if(payments.isEmpty()){
            throw new Exception("Payment not found");
        }
        return payments;
    }

    @Override
    public PaymentResponseDTO findPaymentById(Long id) throws Exception{
        Payment payment = paymentRepository.findById(id).orElseThrow(()->new Exception("Payment not found"));
        return payment.convertToResponse();
    }

    @Override
    public List<PaymentResponseDTO> findByPaymentName(String keyword)throws Exception {
        List<Payment> payments = paymentRepository.findByNameIgnoreCaseContaining(keyword);
        if(payments.isEmpty()){
            throw new Exception("Payment not found");
        }
        return payments.stream()
                .map(Payment::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO addPayment(PaymentRequestDTO paymentRequestDTO) throws Exception {
        Payment payment = paymentRequestDTO.convertToEntity();
        List<Payment> payments = paymentRepository.findByNameIgnoreCaseContaining(paymentRequestDTO.getName());
        if(!payments.isEmpty()){
            throw new Exception("Can't update with same name");
        }
        return paymentRepository.save(payment).convertToResponse();
    }

    @Override
    public PaymentResponseDTO updatePayment(PaymentRequestDTO paymentRequestDTO, Long id) throws Exception {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->new Exception("Payment not found"));
        List<Payment> payments = paymentRepository.findByNameIgnoreCaseContaining(paymentRequestDTO.getName());
        if(!payments.isEmpty()){
            throw new Exception("Can't update with same name");
        }
        payment.setPaymentId(id);
        payment.setName(paymentRequestDTO.getName());
        payment.setPaymentCode(paymentRequestDTO.getPayment_code());
        return paymentRepository.save(payment).convertToResponse();
    }

    @Override
    public void deletePayment(Long paymentId) throws Exception {
        paymentRepository.findById(paymentId).orElseThrow(()->new Exception("Payment not found"));
        paymentRepository.deleteById(paymentId);
    }

}
