package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.entity.HistoryTransaction;
import org.springframework.data.domain.Page;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Pageable;
>>>>>>> ca1431693031e3f94f25a5e28a91d2416f53dfc9
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<HistoryTransaction,Long> {
<<<<<<< HEAD
    List<HistoryTransaction> findAllByOrderByHistoryTransactionIdAsc();
    List<HistoryTransaction> findByBuyerBuyerIdOrderByHistoryTransactionIdAsc(Long id);
    List<HistoryTransaction> findBySellerSellerIdOrderByHistoryTransactionIdAsc(Long id);


=======
    Page<HistoryTransaction> findAll(Pageable pageable);
    List<HistoryTransaction> findByBuyerBuyerId(Long id, Pageable pageable);
    List<HistoryTransaction> findBySellerSellerId(Long id, Pageable pageable);
>>>>>>> ca1431693031e3f94f25a5e28a91d2416f53dfc9
}
