package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.HistoryTransaction;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;

import java.util.List;

public interface TransactionService {
    List<HistoryTransaction> findAllHistory();
    List<HistoryTransactionDetail> findAllHistoryDetail();

    List<HistoryTransaction> findByHistoryBuyerId(Long id) throws Exception;
    List<HistoryTransaction> findByHistorySellerId(Long id) throws Exception;
    List<HistoryTransactionDetail> findByHistoryDetailBuyerId(Long id) throws Exception;
    List<HistoryTransactionDetail> findByHistoryDetailSellerId(Long id) throws Exception;

    HistoryTransaction createTransaction(HistoryTransaction historyTransaction, Long id) throws Exception;

    void deleteHistoryTransactionById(Long id) throws Exception;
    void deleteTransactionDetailById(Long id) throws Exception;

}
