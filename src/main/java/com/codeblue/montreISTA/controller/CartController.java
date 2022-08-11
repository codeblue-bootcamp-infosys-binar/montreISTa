package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.CartRequestDTO;
import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CartService;
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
@Tag(name="08. Cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private static final String Line = "====================";
    private CartService cartService;

    /**
     * findAll
     * @return
     */
    @GetMapping("/carts")
    public ResponseEntity<Object> findAll(){
        try{
            List<CartResponseDTO> result = cartService.findAll();
            List<CartResponseDTO> results = cartService.findAll();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Cart     ====================");
            for(CartResponseDTO cartData : result){
                Map<String, Object> cart = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Cart ID       : " + cartData.getCart_id());
                logger.info("Quantity      : " + cartData.getQuantity());
                logger.info("Buyer ID      : " + cartData.getBuyer_name());
                logger.info("Product ID    : " + cartData.getProduct_name());
                cart.put("Cart ID         ", cartData.getCart_id());
                cart.put("Quantity       ", cartData.getQuantity());
                cart.put("Buyer ID         ", cartData.getBuyer_name());
                cart.put("Product ID        ", cartData.getProduct_name());
                maps.add(cart);
            }
            logger.info("==================== Logger End Get All Cart    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved cart", HttpStatus.OK, results);
        }catch (Exception e){
            logger.info("==================== Logger Start Get All Cart     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,"Cart had no value!")));
            logger.info("==================== Logger End Get All Cart     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed retrieved cart!");
        }
    }

    /**
     * findBybuyer
     * @param keyword
     * @return
     */
    @GetMapping("/cart/buyer")
    public ResponseEntity<Object> findByBuyer(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findByBuyer(keyword);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart! ");
        }
    }

    /**
     * findBySeller
     * @param keyword
     * @return
     */
    @GetMapping("/cart/seller")
    public ResponseEntity<Object> findBySeller(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findBySeller(keyword);
            logger.info(Line + "Logger Start Get Seller " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Seller " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart! ");
        }
    }
    /**
     * findByProductName
     * @param keyword
     * @return
     */
    @GetMapping("/cart/productname")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findByProductName(keyword);
            logger.info(Line + "Logger Start get product name " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get product name " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart! ");
        }
    }

    /**
     * findByCategory
     * @param keyword
     * @return
     */
    @GetMapping("/cart/Category")
    public ResponseEntity<Object> findByCategory(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findByCategory(keyword);
            logger.info(Line + "Logger Start get Category " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End get Category " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart!");
        }
    }

    /**
     * post
     * @param cart
     * @return
     */
    @PostMapping("/addToCart")
    public ResponseEntity<Object> postCart(@RequestBody CartRequestDTO cart) {
        try {
            CartResponseDTO results = cartService.createCart(cart);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create product category!");
        }
    }

    @PostMapping("/cart/wishlist/buyer/{id}")
    public ResponseEntity<Object> postCart(@PathVariable Long id) {
        try {
            List<CartResponseDTO> results = cartService.wishlistToCart(id);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully create product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create product category! ");
        }
    }

    /**
     * Update cart
     * @param id
     * @param cart
     * @return
     */
    @PutMapping("/cart/{id}")
    public ResponseEntity<Object> updateCart(@PathVariable Long id, @RequestBody CartRequestDTO cart) {
        try {
            CartResponseDTO results = cartService.updateCart(cart,id);
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully update product category", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update product category!");
        }
    }

    /**
     * delete
     * @param id
     * @return
     */
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Object> deleteCart(@PathVariable Long id) {
        try {
            cartService.deleteById(id);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully delete cart", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete cart!");
        }
    }
}
