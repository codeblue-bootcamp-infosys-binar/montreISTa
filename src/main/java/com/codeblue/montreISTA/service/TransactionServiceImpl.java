package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.HistoryTransaction;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;
import com.codeblue.montreISTA.repository.HistoryTransactionRepository;
import com.codeblue.montreISTA.repository.TransactionDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private HistoryTransactionRepository historyTransactionRepository;
    private TransactionDetailsRepository transactionDetailsRepository;


    @Override
    public List<HistoryTransaction> findAllHistory() {
        return null;
    }

    @Override
    public List<HistoryTransactionDetail> findAllHistoryDetail() {
        return null;
    }

    @Override
    public List<HistoryTransaction> findByHistoryBuyerId(Long id) throws Exception {
        return null;
    }

    @Override
    public List<HistoryTransaction> findByHistorySellerId(Long id) throws Exception {
        return null;
    }

    @Override
    public List<HistoryTransactionDetail> findByHistoryDetailBuyerId(Long id) throws Exception {
        return null;
    }

    @Override
    public List<HistoryTransactionDetail> findByHistoryDetailSellerId(Long id) throws Exception {
        return null;
    }

    @Override
    public HistoryTransaction createTransaction(HistoryTransaction historyTransaction, Long id) throws Exception {
        return null;
    }


    @Override
    public void deleteHistoryTransactionById(Long id) throws Exception {

    }

    @Override
    public void deleteTransactionDetailById(Long id) throws Exception {

    }
}
