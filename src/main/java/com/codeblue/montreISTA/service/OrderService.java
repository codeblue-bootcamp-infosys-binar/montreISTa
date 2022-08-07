package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllOrder();
    Optional<Order> findById(Long id);
    List<Order> findByProductName(String keyword);
    List<Order> findByBuyerName(String keyword);
    List<Order> findByStoreName(String keyword);
    Order updateOrder(Order order);
    Order createOrder(Order order);
    void deleteOrder(Long id);


}
