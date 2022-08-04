package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.OrderCartDTO;
import com.codeblue.montreISTA.DTO.OrderResponseDTO;
import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.OrderService;
import com.codeblue.montreISTA.service.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class OrderController {

    private OrderServiceImpl orderServiceImpl;

    //GET ALL ORDER
    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrder(){
        try{
            List<Order> orders = orderServiceImpl.findAllOrder();
            List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();

            for(Order order : orders){

                List<OrderCartDTO> cartDTO = new ArrayList<>();

                for(Cart cart : order.getListCart()){
                    OrderCartDTO cartConvert = cart.convertToOrder();
                    cartDTO.add(cartConvert);
                }

                OrderResponseDTO orderDTO = order.convertToResponse(cartDTO);
                orderResponseDTOS.add(orderDTO);

            }

            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, orderResponseDTOS);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }



//    //READ BY ID
//    @GetMapping("/order/{id}")
//    public ResponseEntity<Object> getOrderById(@PathVariable Long orderId) {
//        Optional<Order> orders = orderService.getOrderById(orderId);
//        Order orderget =orders.get();
//        OrderResponseDTO orderResponseDTO = orderget.convertToResponse();
//        return ResponseHandler.generateResponse("Succes Get", HttpStatus.OK, orderResponseDTO);
//    }
}
