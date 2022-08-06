package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.TransactionRequestDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.entity.Transaction;
import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> findAll();

    List<TransactionResponseDTO> findByBuyer(String keyword) throws Exception;
    List<TransactionResponseDTO> findBySeller(String keyword) throws Exception;
    List<TransactionResponseDTO> findByProductName(String keyword) throws Exception;
//    List<Transaction> findByUser(String keyword) throws Exception;

    TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO);
    TransactionResponseDTO updateTransaction(TransactionRequestDTO transactionRequestDTO,Long id) throws Exception;

    void deleteById(Long id) throws Exception;
}
