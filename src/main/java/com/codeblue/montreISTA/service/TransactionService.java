package com.codeblue.montreISTA.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionService {

    ResponseEntity<Object> findAllTransaction(Integer page, String sort, boolean descending);

    ResponseEntity<Object> findAllTransactionDetail(Integer page, String sort, boolean descending);

    ResponseEntity<Object> findByTransactionBuyerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    ResponseEntity<Object> findByTransactionSellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    ResponseEntity<Object> findByTransactionDetailBuyerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    ResponseEntity<Object> findByTransactionDetailSellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception;

    ResponseEntity<Object> findByTransactionId(Long id) throws Exception;

    ResponseEntity<Object> findByTransactionDetailId(Long id) throws Exception;

    ResponseEntity<Object> createTransaction(Authentication authentication) throws Exception;


}
