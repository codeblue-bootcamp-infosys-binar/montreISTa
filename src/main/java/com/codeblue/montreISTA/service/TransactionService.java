package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.entity.HistoryTransactionDetail;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> findAllTransaction();
    List<TransactionDetailResponseDTO> findAllTransactionDetail();

    List<TransactionResponseDTO> findByTransactionBuyerId(Authentication authentication) throws Exception;
    List<TransactionResponseDTO> findByTransactionSellerId(Authentication authentication) throws Exception;
    List<TransactionDetailResponseDTO> findByTransactionDetailBuyerId(Authentication authentication) throws Exception;
    List<TransactionDetailResponseDTO> findByTransactionDetailSellerId(Authentication authentication) throws Exception;

    TransactionResponseDTO findByTransactionId(Long id) throws Exception;
    TransactionDetailResponseDTO findByTransactionDetailId(Long id) throws Exception;

    List<TransactionDetailResponseDTO> createTransaction(Authentication authentication) throws Exception;


    Object getTransactionDetail(String name, String page);

    Integer pageUpdate(String page);

    Page<HistoryTransactionDetail> getTransactionDetailID(Long id, String page);

    Page<HistoryTransactionDetail> getHistoryTransactionDetailID(Long id, String page);

    Page<HistoryTransactionDetail> getHistoryTransactionBySeller(Long id, String page);

    Page<HistoryTransactionDetail> getHistoryTransactionByBuyer(Long id, String page);

    Page<HistoryTransactionDetail> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
