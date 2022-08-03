package com.codeblue.montreISTA.Service;


import com.codeblue.montreISTA.entity.Payment;
import com.codeblue.montreISTA.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentRepository paymentRepository;


    //CREATE
    public Payment addPayment(Payment payment){
        return paymentRepository.save(payment);
    }

    //READ
    public List<Payment> findAllPayment(){
        List<Payment> payments = paymentRepository.findAll();
            return payments;
    }

    //GET BY ID
    public Optional<Payment> findPaymentById(Long id){
        return paymentRepository.findById(id);
    }

    public Payment getReferenceById (Long id) {
        return this.paymentRepository.getById(id);
    }

    //UPDATE
    public Payment updatePayment(Payment payment){
        return paymentRepository.save(payment);
    }

    //DELETE
    public void deletePayment(Long paymentId){
        paymentRepository.deleteById(paymentId);
    }


//    //GET BY NAME
//    public List<Payment> findPaymentByName(String name){
//        List<Payment> paymentByName = paymentRepository.findByName(name);
//        if(paymentByName.isEmpty()){
//            return paymentRepository.findAll();
//        } else {
//            return paymentRepository.findByName(name);
//        }
//    }

}





