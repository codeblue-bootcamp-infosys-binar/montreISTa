package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.service.CartService;
import com.codeblue.montreISTA.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ShippingRepository shippingRepository;
    private final CartService cartService;

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
    public List<OrderResponseDTO> findByBuyerId(Long id) throws Exception {
        List<Order> results = orderRepository.findByListCartBuyerBuyerIdOrderByOrderIdAsc(id);
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
    public OrderResponseCartDTO findByBuyer(String keyword) throws Exception {
        Order results = orderRepository.findFirstByListCartBuyerUserUsernameOrderByOrderIdDesc(keyword).orElseThrow(()->new Exception("Order not found"));
        List<OrderCartDTO> carts = new ArrayList<>();
        for(Cart cart:results.getListCart()){
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
        return results.convertCart(carts);
    }

    @Override
    public OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, String keyword) throws Exception {
        Order order = orderRepository.findFirstByListCartBuyerUserUsernameOrderByOrderIdDesc(keyword).orElseThrow(()->new Exception("Please add product to cart before order"));
        Payment payment = paymentRepository.findById(orderRequestDTO.getPaymentId()).orElseThrow(()->new Exception("Payment Not found"));
        Shipping shipping = shippingRepository.findById(orderRequestDTO.getShippingId()).orElseThrow(()->new Exception("Shipping Not found"));
        orderRequestDTO.convertToEntity(payment, shipping);
        int tempPrice = 0;
        for(Cart cart : order.getListCart()){
            int total = cart.getQuantity() * cart.getProduct().getPrice()+shipping.getPrice();
            tempPrice += total;
        }
        //UPDATE
        order.setPayment(payment);
        order.setShipping(shipping);
        order.setDestinationName(orderRequestDTO.getDestination_name());
        order.setDestinationAddress(orderRequestDTO.getDestination_address());
        order.setDestinationPhone(orderRequestDTO.getDestination_phone());
        order.setZipCode(orderRequestDTO.getZip_code());
        order.setTotalprice(tempPrice);

        //UPDATE TO RESPONSE
        return this.convertDTO(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Authentication authentication) throws Exception {
        List<Order> orders = orderRepository.findByListCartBuyerUserUsernameContaining(authentication.getName());
        if(orders.isEmpty()){
            throw new Exception("Orders not found");
        }
        orderRepository.deleteAll(orders);
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
