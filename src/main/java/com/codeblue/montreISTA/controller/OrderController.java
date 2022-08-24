package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name = "08. Order")
@SecurityRequirement(name = "bearer-key")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private static final String Line = "====================";

    private OrderService orderService;

    @PutMapping("/user/order-now")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, Authentication authentication) throws Exception {
        return orderService.updateOrder(orderRequestDTO, authentication.getName());
    }

    @GetMapping("/user/my-order")
    public ResponseEntity<Object> getOrderByBuyer(Authentication authentication) throws Exception {
        return orderService.findByBuyer(authentication.getName());
    }

    @DeleteMapping("/user/order/delete")
    public ResponseEntity<Object> deleteOrder(Authentication authentication) throws Exception {
            return orderService.deleteOrder(authentication);
    }

    @GetMapping("/dashboard/order/store-name")
    public ResponseEntity<Object> findBySellerStoreName(@Param("keyword") String keyword) throws Exception {
            return orderService.findByStoreName(keyword);
    }

    @GetMapping("/user/pay-my-order")
    public ResponseEntity<Object> payOrder(Authentication authentication) throws Exception {
            return orderService.payOrder(authentication.getName());
    }

    /**
     * FindAll
     *
     * @return
     */
    @GetMapping("/dashboard/orders")
    public ResponseEntity<Object> getAllOrder() {
            return orderService.findAllOrder();
    }

    /**
     * find By Product.ProductName
     *
     * @param
     * @return
     */
    @GetMapping("/dashboard/order/product-name")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword) throws Exception {
            return orderService.findByProductName(keyword);

    }

    /**
     * @param
     * @return
     */
    @GetMapping("/dashboard/order/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) throws Exception {
            return orderService.findOrderById(id);
    }

}
