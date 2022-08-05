package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Product;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllOrder();
    List<Order> findByProductName(String keyword);
    List<Order> findByBuyerName(String keyword);
    List<Order> findByStoreName(String keyword);
    Order createOrder(Order order);
    void deleteOrder(Long id);
//    Optional<Order> getOrderById(Long Id);
//    Order updateOrder(Order order);

}
