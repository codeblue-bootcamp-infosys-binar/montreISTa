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
@Tag(name="08. Order")
@SecurityRequirement(name = "bearer-key")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private static final String Line = "====================";

    private OrderService orderService;

    /**
     * Update Order
     * @return
     */
    @PutMapping("/user/orderNow/buyer")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, Authentication authentication){
        try {
            OrderResponseDTO results = orderService.updateOrder(orderRequestDTO,authentication.getName());
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved order", HttpStatus.CREATED, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }
    
    @GetMapping("/user/my-order")
    public ResponseEntity<Object> getOrderByBuyer(Authentication authentication){
        try{
            OrderResponseCartDTO results = orderService.findByBuyer(authentication.getName());
            logger.info(Line + "Logger Start Get By Buyer " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Buyer " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
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
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted Order", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order no value!");
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
            logger.info(Line + "Logger Start Get Order By Storename  " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Order By Storename " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order no value!");
        }
    }
    
    /**
     * FindAll
     * @return
     */
    @GetMapping("/dashboard/orders")
    public ResponseEntity<Object> getAllOrder(){
        try{
            List<OrderResponseDTO> orders = orderService.findAllOrder();
            logger.info("==================== Logger Start Get All Order    ====================");
            for(OrderResponseDTO orderData : orders){
                logger.info("-------------------------");
                logger.info("Order ID                : " + orderData.getOrderId());
                logger.info("Payment ID              : " + orderData.getPayment_id());
                logger.info("Destionation Name       : " + orderData.getDestination_name());
                logger.info("Destionation Address    : " + orderData.getDestination_address());
                logger.info("Destionation Phone      : " + orderData.getDestination_phone());
                logger.info("Shipping ID             : " + orderData.getShipping_id());
                logger.info("Zip Code                : " + orderData.getZip_code());
                logger.info("Total Price             : " + orderData.getTotal_price());
            }
            logger.info("==================== Logger End Get AlL Order   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, orders);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
        }
    }

    /**
     * Find By Id
     * @param
     * @return
     */
    @GetMapping("/dashboard/order/buyer/{id}")
    public ResponseEntity<Object> findByBuyerUserName(@PathVariable("id") Long id){
        try{
            List<OrderResponseDTO> results = orderService.findByBuyerId(id);
            logger.info(Line + "Logger Start Get Order By Username  " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Order By Username " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
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
            logger.info(Line + "Logger Start Get Order By Productname  " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Order By Productname " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Delete Order!");
        }
    }

    /**
     * @param
     * @return
     */
    @GetMapping("/dashboard/order/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id){
        try{
            OrderResponseDTO results = orderService.findOrderById(id);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
        }
    }
}
