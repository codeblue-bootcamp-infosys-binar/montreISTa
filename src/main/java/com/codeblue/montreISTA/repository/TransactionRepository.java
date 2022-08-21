package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.HistoryTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<HistoryTransaction,Long> {
    List<HistoryTransaction> findAllByOrderByHistoryTransactionIdAsc(Pageable pageable);
    List<HistoryTransaction> findByBuyerBuyerIdOrderByHistoryTransactionIdAsc(Long id, Pageable pageable);
    List<HistoryTransaction> findBySellerSellerIdOrderByHistoryTransactionIdAsc(Long id, Pageable pageable);
}
