package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ShippingRequestDTO;
import com.codeblue.montreISTA.DTO.ShippingResponseDTO;
import com.codeblue.montreISTA.entity.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingService {

    List<ShippingResponseDTO> findAllShipping()throws Exception;

    ShippingResponseDTO findShippingById(Long id) throws Exception;

    ShippingResponseDTO createShipping(ShippingRequestDTO shippingRequestDTO)throws Exception;

    ShippingResponseDTO updateShipping(ShippingRequestDTO shippingRequestDTO,Long id)throws Exception;

    void deleteShipping(Long id)throws Exception;

    List<ShippingResponseDTO> findByName(String keyword)throws Exception;
}
