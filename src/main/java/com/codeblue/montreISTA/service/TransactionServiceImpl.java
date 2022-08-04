package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.Transaction;
import com.codeblue.montreISTA.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findByBuyer(String keyword) throws Exception {
        List<Transaction> results = transactionRepository.findByOrderListCartBuyerUserName(keyword);
        if(results==null){
            throw new Exception("Transaction not found");
        }
        return results;
    }

    @Override
    public List<Transaction> findBySeller(String keyword) throws Exception {
        List<Transaction> results = transactionRepository.findByOrderListCartProductSellerUserIdName(keyword);
        if(results==null){
            throw new Exception("Transaction not found");
        }
        return results;
    }

//    @Override
//    public List<Transaction> findByUser(String keyword) throws Exception {
//        List<Transaction> results = transactionRepository.findByOrderListCartProductSellerUserIdNameOrOrderListCartBuyerUserName(keyword);
//        if(results==null){
//            throw new Exception("Transaction not found");
//        }
//        return results;
//    }

    @Override
    public Transaction createTransaction(Transaction transaction) throws Exception {
        return null;
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) throws Exception {
        return null;
    }

    @Override
    public void deleteById(Long id) throws Exception {

    }
}
