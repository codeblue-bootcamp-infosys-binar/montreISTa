package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.CartRequestDTO;
import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.entity.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CartService {
    ResponseEntity<Object> findAll() throws Exception;

    ResponseEntity<Object> findByBuyer(String keyword) throws Exception;
    ResponseEntity<Object> findBySeller(String keyword) throws Exception;
    ResponseEntity<Object> findByProductName(String keyword) throws Exception;
    ResponseEntity<Object> findByCategory(String keyword)throws Exception;

    ResponseEntity<Object> createCartResponse(CartRequestDTO cartRequestDTO, Authentication authentication) throws Exception;
    ResponseEntity<Object> wishlistToCart(Authentication authentication) throws Exception;
    ResponseEntity<Object> updateCart(CartRequestDTO cartRequestDTO,Long id,Authentication authentication)throws Exception;
    List<CartResponseDTO> convertListDTO(List<Cart> carts);
    CartResponseDTO convertDTO (Cart cart);
    ResponseEntity<Object> deleteById(Long id, Authentication authentication)throws Exception;
}
