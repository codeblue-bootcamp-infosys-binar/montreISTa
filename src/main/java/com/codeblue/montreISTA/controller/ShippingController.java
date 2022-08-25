package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.ShippingRequestDTO;
import com.codeblue.montreISTA.DTO.ShippingResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ShippingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name = "11. Shipping")
@SecurityRequirement(name = "bearer-key")
public class ShippingController {

    @Autowired
    ShippingService shippingService;


    //GET ALL
    @GetMapping("/shipping")
    public ResponseEntity<Object> getAllShipping() throws Exception {
        return shippingService.findAllShipping();

    }


    @GetMapping("/shipping/name")
    public ResponseEntity<Object> findByName(@Param("keyword") String keyword) throws Exception {
        return shippingService.findByName(keyword);
    }

    //GET ONE BY ID
    @GetMapping("/shipping/{id}")
    public ResponseEntity<Object> getShippingById(@PathVariable("id") Long id) throws Exception {

        return shippingService.findShippingById(id);

    }

    //CREATE
    @PostMapping("/dashboard/shipping/create")
    public ResponseEntity<Object> createShipping(@RequestBody ShippingRequestDTO shippingRequestDTO) throws Exception {
        return shippingService.createShipping(shippingRequestDTO);
    }

    //UPDATE
    @PutMapping("/dashboard/shipping/update/{id}")
    public ResponseEntity<Object> updateShipping(@RequestBody ShippingRequestDTO shippingRequestDTO, @PathVariable("id") Long id) throws Exception {
        return shippingService.updateShipping(shippingRequestDTO, id);
    }

    //DELETE
    @DeleteMapping("/dashboard/shipping/delete/{id}")
    public ResponseEntity<Object> deleteShipping(@PathVariable("id") Long id) throws Exception {
        return shippingService.deleteShipping(id);

    }

}
