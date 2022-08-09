package com.codeblue.montreISTA.controller;



import com.codeblue.montreISTA.entity.Shipping;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ShippingServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class ShippingController {

    @Autowired
    ShippingServiceImpl shippingServiceImpl;

    //GET ALL
    @GetMapping("/shipping")
    public ResponseEntity<Object> getAllShipping(){
        try{
            List<Shipping> shippings = shippingServiceImpl.findAllShipping();

            return ResponseHandler.generateResponse("successfully retrieved shippings", HttpStatus.OK, shippings);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @GetMapping("/shipping/name")
    public ResponseEntity<Object> findByName(@Param("keyword") String keyword){
        try{
            List<Shipping> shippings = shippingServiceImpl.findByName(keyword);

            return ResponseHandler.generateResponse("successfully retrieved username", HttpStatus.OK, shippings);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }



    //GET ALL BY SELLER ID
    @GetMapping("/shipping/store/{shipping_id}")
    public ResponseEntity<Object> getAllShippingByShippingId(@PathVariable("shipping_id") Long shippingId){
        try{
            List<Shipping> shipping = shippingServiceImpl.findShippingByShippingId(shippingId);
            return ResponseHandler.generateResponse("successfully retrieved sellers", HttpStatus.OK, shipping);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/shipping/{id}")
    public ResponseEntity<Object> getShippingById(@PathVariable("id") Long id){
        try{
            Optional<Shipping> shipping = shippingServiceImpl.findShippingById(id);
            return ResponseHandler.generateResponse("successfully retrieved shipping", HttpStatus.OK, shipping);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/shipping/create")
    public ResponseEntity<Object> createShipping(@RequestBody Shipping shippingRequestDTO){
        try {
            Shipping shipping = shippingServiceImpl.createShipping(shippingRequestDTO);
            return ResponseHandler.generateResponse("successfully retrieved shipping", HttpStatus.CREATED, shipping);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }

    //UPDATE
    @PutMapping("/shipping/update/{id}")
    public ResponseEntity<Object> updateShipping(@RequestBody Shipping shipping, @PathVariable("id") Long id){
        try{
            Optional<Shipping> targetShipping = shippingServiceImpl.findShippingById(id);
            Shipping updateShipping = targetShipping.get();
            updateShipping.setShippingId(id);
            updateShipping.setName(shipping.getName());
            updateShipping.setPrice(shipping.getPrice());

            shippingServiceImpl.updateShipping(updateShipping);
            return ResponseHandler.generateResponse("successfully updated Shipping", HttpStatus.CREATED, updateShipping);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/shipping/delete/{id}")
    public ResponseEntity<Object> deleteShipping(@PathVariable("id") Long id){
        try{
            shippingServiceImpl.deleteShipping(id);
            return ResponseHandler.generateResponse("successfully deleted shipping", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

}
