package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Seller;
import com.codeblue.montreISTA.helper.ResourceNotFoundException;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.OrderRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.OrderService;
import com.codeblue.montreISTA.service.OrderServiceImpl;
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

    private OrderRepository orderRepository;

    /**
     * FindAll
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrder(){
        try{
            List<Order> orders = orderService.findAllOrder();
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

    /**
     * find By Product.ProductName
     * @param keyword
     * @return
     */
    @GetMapping("/order/productname")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword){
        try{
            List<Order> orders = orderService.findByProductName(keyword);
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

    /**
     * find By Buyer.UserName
     * @param keyword
     * @return
     */
    @GetMapping("/order/buyername")
    public ResponseEntity<Object> findByBuyerUserName(@Param("keyword") String keyword){
        try{
            List<Order> orders = orderService.findByBuyerName(keyword);
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

    /**
     * find By StoreName
     * @param keyword
     * @return
     */
    @GetMapping("/order/storename")
    public ResponseEntity<Object> findBySellerStoreName(@Param("keyword") String keyword){
        try{
            List<Order> orders = orderService.findByStoreName(keyword);
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


    /**
     * Delete Order
     * @return
     */
    @DeleteMapping("/order/delete/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseHandler.generateResponse("successfully deleted product", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }



//    @PostMapping("/order/create")
//    public ResponseEntity<Object> ReservationCreate(@RequestBody OrderRequestDTO orderRequestDTO){
//        try{
//            Order newOrder = orderRequestDTO.convertToEntity();
//            orderService.createOrder(newOrder);
//            OrderResponseDTO orderResponseDTO = newOrder.convertToResponse();
//            return ResponseHandler.generateResponse("successfully create product", HttpStatus.OK, orderResponseDTO);
//        } catch (Exception e) {
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
//        }
//    }


}
