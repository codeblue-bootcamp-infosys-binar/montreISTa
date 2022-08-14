package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.CartRequestDTO;
import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.entity.Cart;
import java.util.List;

public interface CartService {
    List<CartResponseDTO> findAll() throws Exception;

    List<CartResponseDTO> findByBuyer(String keyword) throws Exception;
    List<CartResponseDTO> findBySeller(String keyword) throws Exception;
    List<CartResponseDTO> findByProductName(String keyword) throws Exception;
    List<CartResponseDTO> findByCategory(String keyword)throws Exception;

    CartResponseDTO createCart(CartRequestDTO cartRequestDTO) throws Exception;
    List<CartResponseDTO> wishlistToCart(Long id) throws Exception;
    CartResponseDTO updateCart(CartRequestDTO cartRequestDTO,Long id)throws Exception;
    List<CartResponseDTO> convertListDTO(List<Cart> carts);
    CartResponseDTO convertDTO (Cart cart);
    void deleteById(Long id)throws Exception;
}
