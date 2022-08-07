package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private ShippingRepository shippingRepository;

    public List<OrderResponseDTO> findAllOrder() {
        List<Order> results = orderRepository.findAllByOrderByOrderIdAsc();
        return this.convertListDTO(results);
    }

    @Override
    public OrderResponseDTO findOrderById(Long id) throws Exception {
        return this.convertListDTO2(id);
    }

    //find by id untuk update
    @Override
    public Optional<Order> findById(Long id) throws Exception {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderResponseDTO> findByProductName(String keyword) throws Exception {
        List<Order> results = orderRepository.findByListCartProductProductNameContaining(keyword);
        if(results==null){
            throw new Exception("Orders not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<OrderResponseDTO> findByBuyerName(String keyword) throws Exception {
        List<Order> results = orderRepository.findByListCartBuyerUserUsernameContaining(keyword);
        if(results==null){
            throw new Exception("Orders not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<OrderResponseDTO> findByStoreName(String keyword) throws Exception {
        List<Order> results = orderRepository.findByListCartProductSellerStoreNameContaining(keyword);
        if(results==null){
            throw new Exception("Orders not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Optional<Order> orderId = orderRepository.findById(id);
        if(orderId.isEmpty()){
            throw new Exception("Order not found");
        }
        orderRepository.deleteById(id);
    }


    public List<OrderResponseDTO> convertListDTO(List<Order> orders) {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for (Order order : orders) {
            List<OrderCartDTO> cartDTO = new ArrayList<>();
            for (Cart cart : order.getListCart()) {
                OrderCartDTO cartConvert = cart.convertToOrder();
                cartDTO.add(cartConvert);
            }
            OrderResponseDTO orderDTO = order.convertToResponse(cartDTO);
            orderResponseDTOS.add(orderDTO);
        }
        return orderResponseDTOS;
    }

    public OrderResponseDTO convertListDTO2(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        List<Cart> carts = order.get().getListCart();
        List<OrderCartDTO> cartDTO = new ArrayList<>();

        for(Cart cart : carts){
            OrderCartDTO cartConvert = cart.convertToOrder();
            cartDTO.add(cartConvert);
        }
        OrderResponseDTO orderResponseDTO = order.get().convertToResponse(cartDTO);
        return orderResponseDTO;
    }

}
