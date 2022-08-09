package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.TransactionDetailDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.entity.HistoryTransaction;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> findAllTransaction();
    List<TransactionDetailDTO> findAllTransactionDetail();

    List<TransactionResponseDTO> findByTransactionBuyerId(Long id) throws Exception;
    List<TransactionResponseDTO> findByTransactionSellerId(Long id) throws Exception;
    List<TransactionDetailDTO> findByTransactionDetailBuyerId(Long id) throws Exception;
    List<TransactionDetailDTO> findByTransactionDetailSellerId(Long id) throws Exception;

    TransactionResponseDTO findByTransactionId(Long id) throws Exception;
    TransactionDetailDTO findByTransactionDetailId(Long id) throws Exception;

    String createTransaction(Long id) throws Exception;

}
