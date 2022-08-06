package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByOrderListCartBuyerUserName(String keyword);
    List<Transaction> findByOrderListCartProductSellerUserIdName(String keyword);
    List<Transaction> findByOrderListCartProductProductName(String keyword);
//    List<Transaction> findByOrderListCartProductSellerUserIdNameOrOrderListCartBuyerUserName(String keyword);
    List<Transaction> findAllByOrderByTransactionIdAsc();
}
