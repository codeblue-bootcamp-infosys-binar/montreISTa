package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.CartRequestDTO;
import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.entity.Cart;
import java.util.List;

public interface CartServices {
    List<CartResponseDTO> findAll();

    List<CartResponseDTO> findByBuyer(String keyword) throws Exception;
    List<CartResponseDTO> findBySeller(String keyword) throws Exception;
    List<CartResponseDTO> findByProductName(String keyword) throws Exception;
    List<CartResponseDTO> findByCategory(String keyword)throws Exception;

    CartResponseDTO createCart(CartRequestDTO cartRequestDTO);
    CartResponseDTO updateCart(CartRequestDTO cartRequestDTO,Long id)throws Exception;

    void deleteById(Long id)throws Exception;

}
