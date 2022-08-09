package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private ShippingRepository shippingRepository;
    private CartService cartService;

    public List<OrderResponseDTO> findAllOrder() {
        List<Order> results = orderRepository.findAllByOrderByOrderIdAsc();
        return this.convertListDTO(results);
    }

    @Override
    public OrderResponseDTO findOrderById(Long id) throws Exception {
        Optional<Order> orderId = orderRepository.findById(id);
        if(orderId.isEmpty()){
            throw new Exception("Orders not found");
        }
        return this.convertDTO(orderId.get());
    }

    @Override
    public List<OrderResponseDTO> findByProductName(String keyword) throws Exception {
        List<Order> results = orderRepository.findByListCartProductProductNameContaining(keyword);
        if(results.isEmpty()){
            throw new Exception("Orders not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<OrderResponseDTO> findByBuyerName(String keyword) throws Exception {
        List<Order> results = orderRepository.findByListCartBuyerUserUsernameContaining(keyword);
        if(results.isEmpty()){
            throw new Exception("Orders not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<OrderResponseDTO> findByStoreName(String keyword) throws Exception {
        List<Order> results = orderRepository.findByListCartProductSellerStoreNameContaining(keyword);
        if(results.isEmpty()){
            throw new Exception("Orders not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public OrderResponseCartDTO findByBuyerId(Long id)throws Exception {
        Optional<Order> results = orderRepository.findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(id);
        if(results.isEmpty()){
            throw new Exception("Orders not found");
        }
        List<OrderCartDTO> carts = new ArrayList<>();
        for(Cart cart:results.get().getListCart()){
            List<Photo> photos = cart.getProduct().getPhotos();
            String photoURL;
            boolean check = photos.stream().map(Photo::getPhotoURL).findAny().isEmpty();
            if(check){
                photoURL = "-";
            }else {
                photoURL = photos.get(0).getPhotoURL();
            }
            OrderCartDTO cartDTO = cart.convertToOrder(photoURL);
            carts.add(cartDTO);
        }
        return results.get().convertCart(carts);
    }

    @Override
    public OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, Long id) throws Exception {
        Optional<Order> orderOrder = orderRepository.findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(id);
        Optional<Payment> orderPayment = paymentRepository.findById(orderRequestDTO.getPaymentId());
        Optional<Shipping> orderShipping = shippingRepository.findById(orderRequestDTO.getShippingId());
        if(orderOrder.isEmpty() || orderPayment.isEmpty() || orderShipping.isEmpty()){
            throw new Exception("Please Take Product To Cart First");
        }
        Order order = orderOrder.get();
                orderRequestDTO.convertToEntity(orderPayment.get(), orderShipping.get());
        Integer tempPrice = 0;
        for(Cart cart : order.getListCart()){
            int total = cart.getQuantity() * cart.getProduct().getPrice();
            tempPrice += total;
        }
        tempPrice += order.getShipping().getPrice();
        //UPDATE
        order.setPayment(orderPayment.get());
        order.setShipping(orderShipping.get());
        order.setDestinationName(orderRequestDTO.getDestination_name());
        order.setDestinationAddress(orderRequestDTO.getDestination_address());
        order.setDestinationPhone(orderRequestDTO.getDestination_phone());
        order.setZipCode(orderRequestDTO.getZip_code());
        order.setTotalprice(tempPrice);

        //UPDATE TO RESPONSE
        return this.convertDTO(orderRepository.save(order));
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
        return orders.stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO convertDTO(Order order) {
   List<CartResponseDTO> cartDTO = order.getListCart()
                .stream()
                .map(cartService::convertDTO)
                .collect(Collectors.toList());
   return order.convertToResponse(cartDTO);
    }

}
