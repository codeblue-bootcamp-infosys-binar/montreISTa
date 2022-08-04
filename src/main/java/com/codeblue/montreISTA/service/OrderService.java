package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllOrder();
//    Optional<Order> getOrderById(Long Id);
//    Order createOrder(Order order);
//    void deleteOrderById(Long Id);
//    Order updateOrder(Order order);

}
