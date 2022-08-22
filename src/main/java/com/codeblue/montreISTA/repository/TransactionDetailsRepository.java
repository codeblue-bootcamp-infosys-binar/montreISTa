package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.HistoryTransactionDetail;
import org.springframework.data.domain.Page;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Pageable;
>>>>>>> ca1431693031e3f94f25a5e28a91d2416f53dfc9
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<HistoryTransactionDetail,Long> {
<<<<<<< HEAD
    List<HistoryTransactionDetail> findAllByOrderByTransactionDetailIdAsc();
    List<HistoryTransactionDetail> findByHistoryTransactionBuyerBuyerIdOrderByTransactionDetailIdAsc(Long id);
    List<HistoryTransactionDetail> findByHistoryTransactionSellerSellerIdOrderByTransactionDetailIdAsc(Long id);

    Page<HistoryTransactionDetail> getTransactionDetail(Long id, Object o);

    Page<HistoryTransactionDetail> getTransactionDetail(String name, Object o);
=======
    Page<HistoryTransactionDetail> findAll(Pageable pageable);
    List<HistoryTransactionDetail> findByHistoryTransactionBuyerBuyerId(Long id, Pageable pageable);
    List<HistoryTransactionDetail> findByHistoryTransactionSellerSellerId(Long id, Pageable pageable);
>>>>>>> ca1431693031e3f94f25a5e28a91d2416f53dfc9
}
