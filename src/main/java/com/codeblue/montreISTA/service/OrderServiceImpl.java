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


//    public Optional<Order> findOrderById(Long id) {
//        return orderRepository.findById(id);
//    }

//    @Override
//    public List<Order> getAll() {
//        List<Order> optionalReservation = OrderRepository.findAll();
//        if(optionalReservation.isEmpty()){
//            throw new ResourceNotFoundException("Data Order not exist");
//        }
//        return this.orderRepository.findAll();
//    }

//    @Override
//    public Optional<Order> getOrderById(Long Id) throws ResourceNotFoundException {
//        Optional<Order> optionalOrder = orderRepository.findById(Id);
//        if(optionalOrder.isEmpty()){
//            throw new ResourceNotFoundException("Order not exist with id " + Id);
//        }
//        return this.orderRepository.findById(Id);
//    }
//
//    @Override
//    public Order updateOrder(Order order) throws ResourceNotFoundException {
//        return this.orderRepository.save(order);
//    }
//
//    @Override
//    public Order createOrder(Order order) {
//        return this.orderRepository.save(order);
//    }
//
//    @Override
//    public void deleteOrderById(Long Id) throws ResourceNotFoundException{
//        Optional<Order> optionalOrder = orderRepository.findById(Id);
//        if(optionalOrder.isEmpty()){
//            throw new ResourceNotFoundException("Order not exist with id " + Id);
//        }
//        Order order = orderRepository.getReferenceById(Id);
//        this.orderRepository.delete(order);
//    }
}
