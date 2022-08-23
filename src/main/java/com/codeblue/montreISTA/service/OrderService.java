package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.OrderRequestDTO;
import com.codeblue.montreISTA.DTO.OrderResponseCartDTO;
import com.codeblue.montreISTA.DTO.OrderResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> findAllOrder();
    OrderResponseDTO findOrderById(Long id) throws Exception;

    List<OrderResponseDTO> findByProductName(String keyword) throws Exception;
    List<OrderResponseDTO> findByBuyerId(Long id) throws Exception;
    List<OrderResponseDTO> findByStoreName(String keyword) throws Exception;
    OrderResponseCartDTO findByBuyer(String keyword)throws Exception;
    OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, String keyword) throws Exception;

    void deleteOrder(Authentication authentication) throws Exception;

    OrderResponseCartDTO payOrder(String keyword)throws Exception;
}
