package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByOrderListCartBuyerUserNameIgnoreCaseContaining(String keyword);
    List<Transaction> findByOrderListCartProductSellerUserIdNameIgnoreCaseContaining(String keyword);
    List<Transaction> findByOrderListCartProductProductNameIgnoreCaseContaining(String keyword);
//    List<Transaction> findByOrderListCartProductSellerUserIdNameOrOrderListCartBuyerUserName(String keyword);
    List<Transaction> findAllByOrderByTransactionIdAsc();
}
