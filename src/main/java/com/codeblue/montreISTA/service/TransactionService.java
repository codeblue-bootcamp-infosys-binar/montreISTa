package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {

    List<TransactionResponseDTO> findAllTransaction(Integer page, String sort, boolean descending);

    List<TransactionDetailResponseDTO> findAllTransactionDetail(Integer page, String sort, boolean descending);

    List<TransactionResponseDTO> findByTransactionBuyerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    List<TransactionResponseDTO> findByTransactionSellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    List<TransactionDetailResponseDTO> findByTransactionDetailBuyerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;
    List<TransactionDetailResponseDTO> findByTransactionDetailSellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    TransactionResponseDTO findByTransactionId(Long id) throws Exception;
    TransactionDetailResponseDTO findByTransactionDetailId(Long id) throws Exception;

    List<TransactionDetailResponseDTO> createTransaction(Authentication authentication) throws Exception;

}
