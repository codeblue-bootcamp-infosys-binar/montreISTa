package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.PaymentRequestDTO;
import com.codeblue.montreISTA.DTO.PaymentResponseDTO;
import com.codeblue.montreISTA.controller.PaymentController;
import com.codeblue.montreISTA.entity.Payment;
import com.codeblue.montreISTA.repository.PaymentRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PaymentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private static final String Line = "====================";

    private final PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<Object> findAllPayment() throws Exception {
        try {
            List<PaymentResponseDTO> payments = paymentRepository.findAll().stream()
                    .map(Payment::convertToResponse)
                    .collect(Collectors.toList());
            if (payments.isEmpty()) {
                throw new Exception("Payment not found");
            }
            logger.info("==================== Logger Start Get All Payments     ====================");
            for (PaymentResponseDTO paymentData : payments) {
                logger.info("-------------------------");
                logger.info("Payment ID    : " + paymentData.getPayment_id());
                logger.info("Name          : " + paymentData.getName());
                logger.info("Payment Code  : " + paymentData.getPayment_Code());
            }
            logger.info("==================== Logger End Get All Payments     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("success get all payment", HttpStatus.OK, payments);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Payments     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table has no value")));
            logger.info("==================== Logger End Get All Payments     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table had no value!");
        }

    }

    @Override
    public ResponseEntity<Object> findPaymentById(Long id) throws Exception {
        try {
            Payment payment = paymentRepository.findById(id).orElseThrow(() -> new Exception("Payment not found"));
            PaymentResponseDTO result = payment.convertToResponse();
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("success get by id", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Payment had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByPaymentName(String keyword) throws Exception {
        try {
            List<Payment> payments = paymentRepository.findByNameIgnoreCaseContaining(keyword);
            if (payments.isEmpty()) {
                throw new Exception("Payment not found");
            }
            List<PaymentResponseDTO> results = payments.stream()
                    .map(Payment::convertToResponse).toList();
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Payment had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> addPayment(PaymentRequestDTO paymentRequestDTO) throws Exception {
        try {
            Payment payment = paymentRequestDTO.convertToEntity();
            Optional<Payment> check = paymentRepository.findByNameIgnoreCase(paymentRequestDTO.getName());
            if (check.isPresent()) {
                throw new Exception("Can't update with same name");
            }
            PaymentResponseDTO result = paymentRepository.save(payment).convertToResponse();
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully retrieved payment", HttpStatus.CREATED, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create payment!");
        }
    }

    @Override
    public ResponseEntity<Object> updatePayment(PaymentRequestDTO paymentRequestDTO, Long id) throws Exception {
        try {
            Payment payment = paymentRepository.findById(id).orElseThrow(() -> new Exception("Payment not found"));
            List<Payment> payments = paymentRepository.findByNameIgnoreCaseContaining(paymentRequestDTO.getName());
            if (!payments.isEmpty()) {
                throw new Exception("Can't update with same name");
            }
            payment.setPaymentId(id);
            payment.setName(paymentRequestDTO.getName());
            payment.setPaymentCode(paymentRequestDTO.getPayment_code());
            PaymentResponseDTO result = paymentRepository.save(payment).convertToResponse();
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated payment", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update payment!");
        }
    }

    @Override
    public ResponseEntity<Object> deletePayment(Long paymentId) throws Exception {
        try {
            paymentRepository.findById(paymentId).orElseThrow(() -> new Exception("Payment not found"));
            paymentRepository.deleteById(paymentId);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted payment", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed data payment!");
        }
    }

}
