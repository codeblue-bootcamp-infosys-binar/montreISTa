package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Cart;

import java.util.List;

public interface CartServices {
    List<Cart> findAll();

    List<Cart> findByBuyer(String keyword) throws Exception;
    List<Cart> findBySeller(String keyword) throws Exception;
    List<Cart> findByProductName(String keyword) throws Exception;
    List<Cart> findByCategory(String keyword)throws Exception;

    Cart createCart(Cart cart);
    Cart updateCart(Cart cart,Long id)throws Exception;

    void deleteById(Long id)throws Exception;

}
