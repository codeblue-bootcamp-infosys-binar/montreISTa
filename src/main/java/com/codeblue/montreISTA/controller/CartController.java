package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.ProductCategory;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CartServices;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CartController {
    private CartServices cartServices;

    /**
     * findAll
     * @return
     */
    @GetMapping("/carts")
    public ResponseEntity<Object> findAll(){
        try{
            List<Cart> results = cartServices.findAll();
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
    @GetMapping("/cart/buyer")
    public ResponseEntity<Object> findByBuyer(@Param("keyword") String keyword){
        try{
            List<Cart> results = cartServices.findByBuyer(keyword);
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
    @GetMapping("/cart/seller")
    public ResponseEntity<Object> findBySeller(@Param("keyword") String keyword){
        try{
            List<Cart> results = cartServices.findBySeller(keyword);
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
    @GetMapping("/cart/productname")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword){
        try{
            List<Cart> results = cartServices.findByProductName(keyword);
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
    @GetMapping("/cart/Category")
    public ResponseEntity<Object> findByCategory(@Param("keyword") String keyword){
        try{
            List<Cart> results = cartServices.findByCategory(keyword);
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
    @PostMapping("/cart")
    public ResponseEntity<Object> postCart(@RequestBody Cart cart) {
        try {
            Cart results = cartServices.createCart(cart);
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
    @PutMapping("/cart/{id}")
    public ResponseEntity<Object> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        try {
            Cart results = cartServices.updateCart(cart,id);
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
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Object> deleteCart(@PathVariable Long id) {
        try {
            cartServices.deleteById(id);
            return ResponseHandler.generateResponse("successfully delete cart", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}