package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrder();
    List<Order> findByProductName(String keyword);
    List<Order> findByBuyerName(String keyword);
    List<Order> findByStoreName(String keyword);
    Order createOrder(Order order);
    void deleteOrder(Long id);


}
