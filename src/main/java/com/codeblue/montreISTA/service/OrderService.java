package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.OrderRequestDTO;
import com.codeblue.montreISTA.DTO.OrderResponseCartDTO;
import com.codeblue.montreISTA.DTO.OrderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {

    ResponseEntity<Object> findAllOrder();
    ResponseEntity<Object> findOrderById(Long id) throws Exception;

    ResponseEntity<Object> findByProductName(String keyword) throws Exception;
    List<OrderResponseDTO> findByBuyerId(Long id) throws Exception;
    ResponseEntity<Object> findByStoreName(String keyword) throws Exception;
    ResponseEntity<Object> findByBuyer(String keyword)throws Exception;
    ResponseEntity<Object> updateOrder(OrderRequestDTO orderRequestDTO, String keyword) throws Exception;

    ResponseEntity<Object> deleteOrder(Authentication authentication) throws Exception;

    ResponseEntity<Object> payOrder(String keyword)throws Exception;
}
