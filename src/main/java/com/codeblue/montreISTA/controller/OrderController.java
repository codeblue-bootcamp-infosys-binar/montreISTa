package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="08. Order")
@SecurityRequirement(name = "bearer-key")
public class OrderController {

    private OrderService orderService;

    /**
     * Update Order
     * @return
     */
    @PutMapping("/user/orderNow/buyer")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, Authentication authentication){
        try {
            OrderResponseDTO results = orderService.updateOrder(orderRequestDTO,authentication.getName());
            return ResponseHandler.generateResponse("successfully retrieved order", HttpStatus.CREATED, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }
    /**
     * @param
     * @return
     */
    @GetMapping("/user/order")
    public ResponseEntity<Object> getOrderByBuyer(Authentication authentication){
        try{
            OrderResponseCartDTO results = orderService.findByBuyer(authentication.getName());
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    /**
     * Delete Order
     * @return
     */
    @DeleteMapping("/user/order/delete")
    public ResponseEntity<Object> deleteOrder(Authentication authentication){
        try{
            orderService.deleteOrder(authentication);
            return ResponseHandler.generateResponse("successfully deleted Order", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    /**
     * find By StoreName
     * @param
     * @return
     */
    @GetMapping("/dashboard/order/store-name")
    public ResponseEntity<Object> findBySellerStoreName(@Param("keyword") String keyword){
        try{
            List<OrderResponseDTO> results = orderService.findByStoreName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    /**
     * FindAll
     * @return
     */
    @GetMapping("/dashboard/orders")
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
     * @param
     * @return
     */
    @GetMapping("/dashboard/order/buyer")
    public ResponseEntity<Object> findByBuyerUserName(@RequestParam String keyword){
        try{
            List<OrderResponseDTO> results = orderService.findByBuyerName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    /**
     * find By Product.ProductName
     * @param
     * @return
     */
    @GetMapping("/dashboard/order/product-name")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword){
        try{
            List<OrderResponseDTO> results = orderService.findByProductName(keyword);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
