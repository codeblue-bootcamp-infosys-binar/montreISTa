package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.HistoryTransactionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<HistoryTransactionDetail,Long> {

    Page<HistoryTransactionDetail> findAll(Pageable pageable);

    List<HistoryTransactionDetail> findByHistoryTransactionBuyerBuyerId(Long id, Pageable pageable);

    List<HistoryTransactionDetail> findByHistoryTransactionSellerSellerId(Long id, Pageable pageable);

}