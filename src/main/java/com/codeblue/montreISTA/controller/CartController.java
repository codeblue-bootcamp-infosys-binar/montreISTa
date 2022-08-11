package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.CartRequestDTO;
import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="08. Cart")
public class CartController {
    private CartService cartService;

    /**
     * findAll
     * @return
     */
    @GetMapping("/user/carts")
    public ResponseEntity<Object> findAll(){
        try{
            List<CartResponseDTO> results = cartService.findAll();
            return ResponseHandler.generateResponse("successfully retrieved cart", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * findBybuyer
     * @param keyword
     * @return
     */
    @GetMapping("/user/cart/buyer")
    public ResponseEntity<Object> findByBuyer(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findByBuyer(keyword);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * findBySeller
     * @param keyword
     * @return
     */
    @GetMapping("/user/cart/seller")
    public ResponseEntity<Object> findBySeller(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findBySeller(keyword);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    /**
     * findByProductName
     * @param keyword
     * @return
     */
    @GetMapping("/user/cart/productname")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findByProductName(keyword);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * findByCategory
     * @param keyword
     * @return
     */
    @GetMapping("/user/cart/category")
    public ResponseEntity<Object> findByCategory(@Param("keyword") String keyword){
        try{
            List<CartResponseDTO> results = cartService.findByCategory(keyword);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    /**
     * post
     * @param cart
     * @return
     */
    @PostMapping("/user/add-to-cart")
    public ResponseEntity<Object> postCart(@RequestBody CartRequestDTO cart) {
        try {
            CartResponseDTO results = cartService.createCart(cart);
            return ResponseHandler.generateResponse("successfully create product category", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping("/user/cart/wishlist/buyer/{id}")
    public ResponseEntity<Object> postCart(@PathVariable Long id) {
        try {
            List<CartResponseDTO> results = cartService.wishlistToCart(id);
            return ResponseHandler.generateResponse("successfully create product category", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * Update cart
     * @param id
     * @param cart
     * @return
     */
    @PutMapping("/user/cart/{id}")
    public ResponseEntity<Object> updateCart(@PathVariable Long id, @RequestBody CartRequestDTO cart) {
        try {
            CartResponseDTO results = cartService.updateCart(cart,id);
            return ResponseHandler.generateResponse("successfully update product category", HttpStatus.OK, results);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * delete
     * @param id
     * @return
     */
    @DeleteMapping("/user/cart/{id}")
    public ResponseEntity<Object> deleteCart(@PathVariable Long id) {
        try {
            cartService.deleteById(id);
            return ResponseHandler.generateResponse("successfully delete cart", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
