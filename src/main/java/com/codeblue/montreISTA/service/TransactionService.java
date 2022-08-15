package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> findAllTransaction();
    List<TransactionDetailResponseDTO> findAllTransactionDetail();

    List<TransactionResponseDTO> findByTransactionBuyerId(Authentication authentication) throws Exception;
    List<TransactionResponseDTO> findByTransactionSellerId(Authentication authentication) throws Exception;
    List<TransactionDetailResponseDTO> findByTransactionDetailBuyerId(Authentication authentication) throws Exception;
    List<TransactionDetailResponseDTO> findByTransactionDetailSellerId(Authentication authentication) throws Exception;

    TransactionResponseDTO findByTransactionId(Long id) throws Exception;
    TransactionDetailResponseDTO findByTransactionDetailId(Long id) throws Exception;

    List<TransactionDetailResponseDTO> createTransaction(Authentication authentication) throws Exception;

}
