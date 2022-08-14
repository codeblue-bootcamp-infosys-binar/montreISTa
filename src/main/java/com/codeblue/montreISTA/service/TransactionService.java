package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.TransactionDetailDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.entity.HistoryTransaction;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> findAllTransaction();
    List<TransactionDetailDTO> findAllTransactionDetail();

    List<TransactionResponseDTO> findByTransactionBuyerId(Authentication authentication) throws Exception;
    List<TransactionResponseDTO> findByTransactionSellerId(Authentication authentication) throws Exception;
    List<TransactionDetailDTO> findByTransactionDetailBuyerId(Authentication authentication) throws Exception;
    List<TransactionDetailDTO> findByTransactionDetailSellerId(Authentication authentication) throws Exception;

    TransactionResponseDTO findByTransactionId(Long id) throws Exception;
    TransactionDetailDTO findByTransactionDetailId(Long id) throws Exception;

    List<TransactionDetailDTO> createTransaction(Authentication authentication) throws Exception;

}
