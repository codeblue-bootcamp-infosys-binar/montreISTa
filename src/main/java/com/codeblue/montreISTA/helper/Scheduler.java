package com.codeblue.montreISTA.helper;

import com.codeblue.montreISTA.controller.CartController;
import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.repository.OrderRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.service.OrderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

@Component
@AllArgsConstructor
@Transactional
public class Scheduler {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ProductRepository productRepository;

    //    @Scheduled(cron = "59 */3 * * * ?") // staging , setiap 3 menit pada menit detik 59
    @Scheduled(cron = "59 55-59 * * * ?") // production , setiap jam pada menit 55:59 - 59:59
    public void cronSchedule() {
        ZonedDateTime zoneNow = ZonedDateTime.now(TimeZone.getTimeZone("GMT/Zulu").toZoneId());
//        ZonedDateTime zoneNow = ZonedDateTime.now(TimeZone.getTimeZone("Asia/Bangkok").toZoneId());
        List<Order> orders = orderRepository.findByIsPay(false);
        AtomicReference<String> message = new AtomicReference<>("Schedule work, but nothing to delete");
        orders.forEach(order -> {
            boolean check = order.getDestinationName() != null
                    && order.getDestinationAddress() != null
                    && order.getDestinationPhone() != null
                    && order.getZipCode() != null;
            if (check) {
//                if (zoneNow.getHour() == order.getModifiedAt().getHour() && zoneNow.minusMinutes(2).getMinute() >= order.getModifiedAt().getMinute()) { //staging, lakukan task jika data > 2 menit
                if (zoneNow.getHour() == order.getModifiedAt().getHour() && zoneNow.minusDays(1).getDayOfMonth() >= order.getModifiedAt().getDayOfMonth()) { //production, lakukan task jika data > 1 hari
                    order.getListCart().forEach(
                            cart -> {
                                try {
                                    Product product = productRepository.findById(cart.getProduct().getId()).orElseThrow(() -> new Exception("Product not found"));
                                    product.setStock(product.getStock() + cart.getQuantity());
                                    productRepository.save(product);

                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
                    message.set("success delete order id : " + order.getOrderId());
                    orderRepository.delete(order);
                }
            }
        });

        String line = "====================";
        logger.info(line);
        logger.info("The time is now {} ", zoneNow);
        logger.info(String.valueOf(message));
        logger.info(line);
    }
}
