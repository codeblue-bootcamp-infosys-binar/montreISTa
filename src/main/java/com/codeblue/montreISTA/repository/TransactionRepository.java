package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.HistoryTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<HistoryTransaction,Long> {

    Page<HistoryTransaction> findAll(Pageable pageable);
    List<HistoryTransaction> findByBuyerBuyerId(Long id, Pageable pageable);
    List<HistoryTransaction> findBySellerSellerId(Long id, Pageable pageable);

}
