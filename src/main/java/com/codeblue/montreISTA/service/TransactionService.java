package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();

    List<Transaction> findByBuyer(String keyword) throws Exception;
    List<Transaction> findBySeller(String keyword) throws Exception;
//    List<Transaction> findByUser(String keyword) throws Exception;

    Transaction createTransaction(Transaction transaction) throws Exception;
    Transaction updateTransaction(Transaction transaction) throws Exception;

    void deleteById(Long id) throws Exception;
}
