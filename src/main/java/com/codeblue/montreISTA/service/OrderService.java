package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.OrderRequestDTO;
import com.codeblue.montreISTA.DTO.OrderResponseCartDTO;
import com.codeblue.montreISTA.DTO.OrderResponseDTO;
import java.util.List;


public interface OrderService {

    List<OrderResponseDTO> findAllOrder();
    OrderResponseDTO findOrderById(Long id) throws Exception;
//    Optional<Order> findById (Long id) throws Exception;
    List<OrderResponseDTO> findByProductName(String keyword) throws Exception;
    List<OrderResponseDTO> findByBuyerName(String keyword) throws Exception;
    List<OrderResponseDTO> findByStoreName(String keyword) throws Exception;
    OrderResponseCartDTO findByBuyerId(Long id)throws Exception;
    OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, Long id) throws Exception;
//    OrderResponsePost createOrder(OrderRequestDTO orderRequestDTO) throws Exception;
    void deleteOrder(Long id) throws Exception;


}
