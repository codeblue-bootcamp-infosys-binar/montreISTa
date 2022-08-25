package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.controller.OrderController;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CartService;
import com.codeblue.montreISTA.service.OrderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ShippingRepository shippingRepository;
    private final CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private static final String Line = "====================";

    public ResponseEntity<Object> findAllOrder() {
        try {
            List<Order> orders = orderRepository.findAllByOrderByOrderIdAsc();
            List<OrderResponseDTO> results = this.convertListDTO(orders);
            if(results.isEmpty()){
                throw new Exception("Order Not Found");
            }
            logger.info("==================== Logger Start Get All Order    ====================");
            for (OrderResponseDTO orderData : results) {
                logger.info("-------------------------");
                logger.info("Order ID                : " + orderData.getOrderId());
                logger.info("Payment ID              : " + orderData.getPayment_id());
                logger.info("Destination Name       : " + orderData.getDestination_name());
                logger.info("Destination Address    : " + orderData.getDestination_address());
                logger.info("Destination Phone      : " + orderData.getDestination_phone());
                logger.info("Shipping ID             : " + orderData.getShipping_id());
                logger.info("Zip Code                : " + orderData.getZip_code());
                logger.info("Total Price             : " + orderData.getTotal_price());
            }
            logger.info("==================== Logger End Get AlL Order   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findOrderById(Long id) {
        try {
            Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("Order Not found"));
            OrderResponseDTO results = this.convertDTO(order);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByProductName(String keyword) throws Exception {
        try {
            List<Order> orders = orderRepository.findByListCartProductProductNameIgnoreCaseContaining(keyword);
            if (orders.isEmpty()) {
                throw new Exception("Orders not found");
            }
            List<OrderResponseDTO> results = this.convertListDTO(orders);
            logger.info(Line + "Logger Start Get Order By Product name  " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Order By Product name " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Found Order!");
        }
    }

    @Override
    public List<OrderResponseDTO> findByBuyerId(Long id) throws Exception {
        List<Order> results = orderRepository.findByListCartBuyerBuyerIdOrderByOrderIdAsc(id);
        if (results.isEmpty()) {
            throw new Exception("Orders not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public ResponseEntity<Object> findByStoreName(String keyword) {
        try {
            List<Order> Orders = orderRepository.findByListCartProductSellerStoreNameIgnoreCaseContaining(keyword);
            if (Orders.isEmpty()) {
                throw new Exception("Orders not found");
            }
            List<OrderResponseDTO> results = this.convertListDTO(Orders);
            logger.info(Line + "Logger Start Get Order By Store name  " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Order By Store name " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findByBuyer(String keyword) {
        try {
            Order orders = orderRepository.findFirstByListCartBuyerUserUsernameOrderByOrderIdDesc(keyword).orElseThrow(() -> new Exception("Order not found"));
            List<OrderCartDTO> carts = new ArrayList<>();
            for (Cart cart : orders.getListCart()) {
                List<Photo> photos = cart.getProduct().getPhotos();
                String photoURL;
                boolean check = photos.stream().map(Photo::getPhotoURL).findAny().isEmpty();
                if (check) {
                    photoURL = "-";
                } else {
                    photoURL = photos.get(0).getPhotoURL();
                }
                OrderCartDTO cartDTO = cart.convertToOrder(photoURL);
                carts.add(cartDTO);
            }
            OrderResponseCartDTO results = orders.convertCart(carts);
            logger.info(Line + "Logger Start Get By Buyer " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Buyer " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> updateOrder(OrderRequestDTO orderRequestDTO, String keyword) {
        try {

            Order order = orderRepository.findFirstByListCartBuyerUserUsernameOrderByOrderIdDesc(keyword).orElseThrow(() -> new Exception("Please add product to cart before order"));
            Payment payment = paymentRepository.findByNameIgnoreCase(orderRequestDTO.getPayment()).orElseThrow(() -> new Exception("Payment Not found"));
            Shipping shipping = shippingRepository.findByNameIgnoreCase(orderRequestDTO.getShipping()).orElseThrow(() -> new Exception("Shipping Not found"));
            orderRequestDTO.convertToEntity(payment, shipping);
            int tempPrice = 0;
            for (Cart cart : order.getListCart()) {
                int total = cart.getQuantity() * cart.getProduct().getPrice() + shipping.getPrice();
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
            OrderResponseDTO results = this.convertDTO(orderRepository.save(order));
            logger.info(Line + "Logger Start Update" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Update" + Line);
            return ResponseHandler.generateResponse("successfully make order", HttpStatus.CREATED, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Order");
        }
    }

    @Override
    public ResponseEntity<Object> deleteOrder(Authentication authentication) {
        try {
            List<Order> orders = orderRepository.findByListCartBuyerUserUsernameContaining(authentication.getName());
            if (orders.isEmpty()) {
                throw new Exception("Orders not found");
            }
            orderRepository.deleteAll(orders);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted Order", HttpStatus.OK, "Success delete");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed delete order!");
        }
    }

    @Override
    public ResponseEntity<Object> payOrder(String keyword) {
        try {
            Order orderGet = orderRepository.findFirstByListCartBuyerUserUsernameOrderByOrderIdDesc(keyword).orElseThrow(() -> new Exception("Order not found"));
            if(orderGet.getPayment()==null|| orderGet.getShipping()==null
                    ||orderGet.getDestinationName()==null
                    ||orderGet.getDestinationAddress()==null
                    ||orderGet.getDestinationPhone()==null){
                throw new Exception("Please order now first and input necessary info !");
            }
            boolean checkPay = true;
            orderGet.setIsPay(checkPay);
            Order order = orderRepository.save(orderGet);
            OrderResponseDTO results = this.convertDTO(order);
            logger.info(Line + "Logger Start Get By Buyer " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Buyer " + Line);
            return ResponseHandler.generateResponse("successfully retrieved orders", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Order had no value!");
        }
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
