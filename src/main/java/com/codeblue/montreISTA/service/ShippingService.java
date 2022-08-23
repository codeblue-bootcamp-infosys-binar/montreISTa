package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.ShippingRequestDTO;
import com.codeblue.montreISTA.DTO.ShippingResponseDTO;
import com.codeblue.montreISTA.entity.Shipping;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ShippingService {

    ResponseEntity<Object> findAllShipping()throws Exception;

    ResponseEntity<Object> findShippingById(Long id) throws Exception;

    ResponseEntity<Object> createShipping(ShippingRequestDTO shippingRequestDTO)throws Exception;

    ResponseEntity<Object> updateShipping(ShippingRequestDTO shippingRequestDTO,Long id)throws Exception;

    ResponseEntity<Object> deleteShipping(Long id)throws Exception;

    ResponseEntity<Object> findByName(String keyword)throws Exception;
}
