package com.codeblue.montreISTA.controller;


import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.BuyerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name = "05. Buyer")
@SecurityRequirement(name = "bearer-key")
public class BuyerController {

    private final BuyerService buyerService;

    //CREATE
    @GetMapping("/user/buyers/login-as-buyer")
    public ResponseEntity<Object> createBuyer(Authentication authentication) throws Exception {
        return buyerService.createBuyer(authentication);
    }

    //GET ALL
    @GetMapping("/dashboard/buyer")
    public ResponseEntity<Object> getAllBuyer() throws Exception {
        return buyerService.findAllBuyer();
    }

    @GetMapping("/dashboard/buyer/find-by-username")
    public ResponseEntity<Object> findByUsername(@Param("keyword") String keyword) throws Exception {
        return buyerService.findByUsername(keyword);
    }

    //DELETE
    @DeleteMapping("/user/buyers/delete/{id}")
    public ResponseEntity<Object> deleteBuyer(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        return buyerService.deleteBuyer(id, authentication);
    }
}

