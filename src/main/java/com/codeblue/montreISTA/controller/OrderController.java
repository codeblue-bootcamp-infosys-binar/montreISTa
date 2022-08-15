package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="09. Order")
@SecurityRequirement(name = "bearer-key")
public class OrderController {

    private OrderService orderService;

    /**
     * FindAll
     * @return
     */
    @GetMapping("/user/orders")
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
    @GetMapping("/user/order/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id){
        try{
            OrderResponseDTO results = orderService.findOrderById(id);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/user/order/buyer/{id}")
    public ResponseEntity<Object> getOrderByBuyerId(@PathVariable("id") Long id){
        try{
            OrderResponseCartDTO results = orderService.findByBuyerId(id);
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
    @GetMapping("/user/order/productname")
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
    @GetMapping("/user/order/buyername")
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
    @GetMapping("/user/order/storename")
    public ResponseEntity<Object> findBySellerStoreName(@Param("keyword") String keyword){
        try{
            List<OrderResponseDTO> results = orderService.findByStoreName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * Update Order
     * @return
     */
    @PutMapping("/user/orderNow/buyer/{id}")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, @PathVariable("id") Long id){
        try {
            OrderResponseDTO results = orderService.updateOrder(orderRequestDTO,id);
            return ResponseHandler.generateResponse("successfully retrieved order", HttpStatus.CREATED, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }


    /**
     * Delete Order
     * @return
     */
    @DeleteMapping("/user/order/delete/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseHandler.generateResponse("successfully deleted Order", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }


}
