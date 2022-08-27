package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.OrderRequestDTO;
import com.codeblue.montreISTA.DTO.OrderResponseCartDTO;
import com.codeblue.montreISTA.DTO.OrderResponseDTO;
import com.codeblue.montreISTA.entity.Order;
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
    ResponseEntity<Object> findBySeller(String keyword)throws Exception;
    ResponseEntity<Object> updateOrder(OrderRequestDTO orderRequestDTO, String keyword) throws Exception;

    ResponseEntity<Object> deleteOrder(Authentication authentication) throws Exception;

    ResponseEntity<Object> payOrder(String keyword)throws Exception;
    List<OrderResponseDTO> convertListDTO(List<Order> orders);
    OrderResponseDTO convertDTO(Order order);
    void checkInput(String name, String phone, String address, String zip_code) throws Exception;
}
