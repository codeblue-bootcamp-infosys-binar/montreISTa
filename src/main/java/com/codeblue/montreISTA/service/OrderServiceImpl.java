package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.helper.ResourceNotFoundException;
import com.codeblue.montreISTA.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    OrderRepository orderRepository;

    public List<Order> findAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    @Override
    public List<Order> findByProductName(String keyword) {
        List<Order> orderProduct = orderRepository.findByListCartProductProductNameContaining(keyword);
        return orderProduct;
    }

    @Override
    public List<Order> findByBuyerName(String keyword) {
        List<Order> orderProduct = orderRepository.findByListCartBuyerUserUsernameContaining(keyword);
        return orderProduct;
    }

    @Override
    public List<Order> findByStoreName(String keyword) {
        List<Order> orderProduct = orderRepository.findByListCartProductSellerStoreNameContaining(keyword);
        return orderProduct;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


//    public Optional<Order> findOrderById(Long id) {
//        return orderRepository.findById(id);
//    }


//    @Override
//    public Optional<Order> getOrderById(Long Id) throws ResourceNotFoundException {
//        Optional<Order> optionalOrder = orderRepository.findById(Id);
//        if(optionalOrder.isEmpty()){
//            throw new ResourceNotFoundException("Order not exist with id " + Id);
//        }
//        return this.orderRepository.findById(Id);
//    }


//    @Override
//    public Order createOrder(Order order) {
//        return this.orderRepository.save(order);
//    }

}
