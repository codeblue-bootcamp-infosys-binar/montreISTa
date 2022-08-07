package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.OrderRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.OrderService;
import com.codeblue.montreISTA.service.PaymentService;
import com.codeblue.montreISTA.service.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    private PaymentService paymentService;
    private ShippingService shippingService;

    private OrderRepository orderRepository;


    /**
     * FindAll
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrder(){
        try{
            List<OrderResponseDTO> results = orderService.findAllOrder();
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * Find By Id
     * @param id
     * @return
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id){
        try{
            OrderResponseDTO results = orderService.findOrderById(id);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * find By Product.ProductName
     * @param keyword
     * @return
     */
    @GetMapping("/order/productname")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword){
        try{
            List<OrderResponseDTO> results = orderService.findByProductName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * find By Buyer.UserName
     * @param keyword
     * @return
     */
    @GetMapping("/order/buyername")
    public ResponseEntity<Object> findByBuyerUserName(@Param("keyword") String keyword){
        try{
            List<OrderResponseDTO> results = orderService.findByBuyerName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * find By StoreName
     * @param keyword
     * @return
     */
    @GetMapping("/order/storename")
    public ResponseEntity<Object> findBySellerStoreName(@Param("keyword") String keyword){
        try{
            List<OrderResponseDTO> results = orderService.findByStoreName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * Create Order
     * @return
     */
    @PostMapping("/order/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        try {
            Optional<Payment> orderPayment = paymentService.findPaymentById(orderRequestDTO.getPaymentId());
            Payment payment = orderPayment.get();
            Optional<Shipping> orderShipping = shippingService.findShippingById(orderRequestDTO.getShippingId());
            Shipping shipping = orderShipping.get();

            Order newOrder = orderRequestDTO.convertToEntity(payment,shipping);

            orderService.createOrder(newOrder);

            OrderResponsePost orderResponsePost = newOrder.convertToResponsePost();

            return ResponseHandler.generateResponse("successfully retrieved order", HttpStatus.CREATED, orderResponsePost);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    /**
     * Update Order
     * @return
     */
    @PutMapping("/order/update{id}")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, @PathVariable("id") Long id){
        try {
            Optional<Order> orderOrder = orderService.findById(id);
            Order updateOrder = orderOrder.get();
            Optional<Payment> orderPayment = paymentService.findPaymentById(orderRequestDTO.getPaymentId());
            Payment payment = orderPayment.get();
            Optional<Shipping> orderShipping = shippingService.findShippingById(orderRequestDTO.getShippingId());
            Shipping shipping = orderShipping.get();

            Order order = orderRequestDTO.convertToEntity(payment, shipping);
            Integer tempPrice = 0;
            for(Cart cart : updateOrder.getListCart()){
                Integer total = cart.getQuantity() * cart.getProduct().getPrice();
                tempPrice += total;
            }
            tempPrice += order.getShipping().getPrice();

            //UPDATE
            updateOrder.setOrderId(id);
            updateOrder.setPayment(order.getPayment());
            updateOrder.setShipping(order.getShipping());
            updateOrder.setTotalprice(tempPrice);

            //UPDATE TO RESPONSE
            Order orderSave = orderService.updateOrder(updateOrder);
            List<OrderCartDTO> cartDTO = new ArrayList<>();

            for(Cart cart : orderSave.getListCart()){
                OrderCartDTO cartConvert = cart.convertToOrder();
                cartDTO.add(cartConvert);
            }

            OrderResponseDTO orderDTO = orderSave.convertToResponse(cartDTO);

            return ResponseHandler.generateResponse("successfully retrieved order", HttpStatus.CREATED, orderDTO);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }


    /**
     * Delete Order
     * @return
     */
    @DeleteMapping("/order/delete/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseHandler.generateResponse("successfully deleted Order", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }


}
