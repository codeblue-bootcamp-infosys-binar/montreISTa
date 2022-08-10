package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.HistoryTransaction;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<HistoryTransactionDetail,Long> {
    List<HistoryTransactionDetail> findAllByOrderByTransactionDetailIdAsc();
    List<HistoryTransactionDetail> findByHistoryTransactionBuyerBuyerIdOrderByTransactionDetailIdAsc(Long id);
    List<HistoryTransactionDetail> findByHistoryTransactionSellerSellerIdOrderByTransactionDetailIdAsc(Long id);
}
