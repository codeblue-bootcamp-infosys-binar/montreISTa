package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.OrderRequestDTO;
import com.codeblue.montreISTA.DTO.OrderResponseDTO;
import com.codeblue.montreISTA.DTO.OrderResponsePost;
import com.codeblue.montreISTA.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponseDTO> findAllOrder();
    OrderResponseDTO findOrderById(Long id) throws Exception;
//    Optional<Order> findById (Long id) throws Exception;
    List<OrderResponseDTO> findByProductName(String keyword) throws Exception;
    List<OrderResponseDTO> findByBuyerName(String keyword) throws Exception;
    List<OrderResponseDTO> findByStoreName(String keyword) throws Exception;
    OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, Long id) throws Exception;
//    OrderResponsePost createOrder(OrderRequestDTO orderRequestDTO) throws Exception;
    void deleteOrder(Long id) throws Exception;


}
