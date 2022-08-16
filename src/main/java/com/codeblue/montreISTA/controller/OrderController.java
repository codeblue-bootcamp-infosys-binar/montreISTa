package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Tag(name="09. Order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private static final String Line = "====================";

    private OrderService orderService;

    /**
     * FindAll
     * @return
     */
    @GetMapping("/user/orders")
    public ResponseEntity<Object> getAllOrder(){
        try{
            List<OrderResponseDTO> orders = orderService.findAllOrder();
            List<OrderResponseDTO> results = orderService.findAllOrder();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Order    ====================");
            for(OrderResponseDTO orderData : orders){
                Map<String, Object> order = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Order ID                : " + orderData.getOrderId());
                logger.info("Payment ID              : " + orderData.getPayment_id());
                logger.info("Destionation Name       : " + orderData.getDestination_name());
                logger.info("Destionation Address    : " + orderData.getDestination_address());
                logger.info("Destionation Phone      : " + orderData.getDestination_phone());
                logger.info("Shipping ID             : " + orderData.getShipping_id());
                logger.info("Zip Code                : " + orderData.getZip_code());
                logger.info("Total Price             : " + orderData.getTotal_price());
                order.put("Order ID                : ", orderData.getOrderId());
                order.put("Payment ID              : ", orderData.getPayment_id());
                order.put("Destionation Name       : ", orderData.getDestination_name());
                order.put("Destionation Address    : ", orderData.getDestination_address());
                order.put("Destionation Phone      : ", orderData.getDestination_phone());
                order.put("Shipping ID             : ", orderData.getShipping_id());
                order.put("Zip Code                : ", orderData.getZip_code());
                order.put("Total Price             : ", orderData.getTotal_price());
                maps.add(order);
            }
            logger.info("==================== Logger End Get AlL Order   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
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

    @GetMapping("/user/order/buyer/{id}")
    public ResponseEntity<Object> getOrderByBuyerId(@PathVariable("id") Long id){
        try{
            OrderResponseCartDTO results = orderService.findByBuyerId(id);
            logger.info(Line + "Logger Start Get Buyer Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Buyer Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
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
            logger.info(Line + "Logger Start Get Order By Productname  " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Order By Productname " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order no value!");
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
            logger.info(Line + "Logger Start Get Order By Username  " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Order By Username " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order no value!");
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
     * Update Order
     * @return
     */
    @PutMapping("/user/orderNow/buyer/{id}")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, @PathVariable("id") Long id){
        try {
            OrderResponseDTO results = orderService.updateOrder(orderRequestDTO,id);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved order", HttpStatus.OK, results);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed update order!");
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
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted Order", HttpStatus.OK, null);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Delete Order!");
        }

    }


}
