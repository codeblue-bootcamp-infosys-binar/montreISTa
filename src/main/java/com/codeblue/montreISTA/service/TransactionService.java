package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.HistoryTransaction;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;

import java.util.List;

public interface TransactionService {
    List<HistoryTransaction> findAllTransaction();
    List<HistoryTransactionDetail> findAllTransactionDetail();

    List<HistoryTransaction> findByTransactionBuyerId(Long id) throws Exception;
    List<HistoryTransaction> findByTransactionSellerId(Long id) throws Exception;
    List<HistoryTransactionDetail> findByTransactionDetailBuyerId(Long id) throws Exception;
    List<HistoryTransactionDetail> findByTransactionDetailSellerId(Long id) throws Exception;
    String createTransaction(Long id) throws Exception;

}
