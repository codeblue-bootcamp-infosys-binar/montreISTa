package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.entity.HistoryTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<HistoryTransaction,Long> {
    List<HistoryTransaction> findAllByOrderByHistoryTransactionIdAsc();
    List<HistoryTransaction> findByBuyerBuyerIdOrderByHistoryTransactionIdAsc(Long id);
    List<HistoryTransaction> findBySellerSellerIdOrderByHistoryTransactionIdAsc(Long id);


}
