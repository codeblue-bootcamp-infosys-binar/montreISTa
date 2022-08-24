package com.codeblue.montreISTA.service.implement;


import com.codeblue.montreISTA.DTO.ShippingRequestDTO;
import com.codeblue.montreISTA.DTO.ShippingResponseDTO;
import com.codeblue.montreISTA.controller.ShippingController;
import com.codeblue.montreISTA.entity.Shipping;
import com.codeblue.montreISTA.repository.ShippingRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.ShippingService;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;
    private static final Logger logger = LoggerFactory.getLogger(ShippingController.class);
    private static final String Line = "====================";

    @Override
    public ResponseEntity<Object> findAllShipping() throws Exception {
        try {
            List<Shipping> shippings = shippingRepository.findAll();
            if (shippings.isEmpty()) {
                throw new Exception("Shipping not found");
            }
            logger.info("==================== Logger Start Get All Shipping    ====================");
            for (Shipping shippingData : shippings) {
                logger.info("-------------------------");
                logger.info("Shipping ID  : " + shippingData.getShippingId());
                logger.info("Name         : " + shippingData.getName());
                logger.info("Price        : " + shippingData.getPrice());
            }
            logger.info("==================== Logger End Get All Shipping    ====================");
            logger.info(" ");
            List<ShippingResponseDTO> results = shippings.stream()
                    .map(Shipping::convertToResponse).toList();
            return ResponseHandler.generateResponse("successfully retrieved shippings", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Shippings    ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table has no value")));
            logger.info("==================== Logger End Get All Shippings     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table has no value");
        }
    }

    @Override
    public ResponseEntity<Object> findShippingById(Long id) throws Exception {
        try {
            ShippingResponseDTO shipping = shippingRepository.findById(id)
                    .orElseThrow(() -> new Exception("Shipping not found"))
                    .convertToResponse();
            logger.info(Line + " Logger Start Get By id Shipping " + Line);
            logger.info(String.valueOf(shipping));
            logger.info(Line + " Logger End Get By id Shipping " + Line);
            return ResponseHandler.generateResponse("success get by id", HttpStatus.OK, shipping);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found!");
        }
    }


    @Override
    public ResponseEntity<Object> createShipping(ShippingRequestDTO shippingRequestDTO) throws Exception {
        try {
            Shipping shipping = shippingRequestDTO.convertToEntity();
            Optional<Shipping> check = shippingRepository.findByNameIgnoreCase(shippingRequestDTO.getName());
            if (check.isPresent()) {
                throw new Exception("this name not unique");
            }
            ShippingResponseDTO result = shippingRepository.save(shipping).convertToResponse();
            logger.info(Line + " Logger Start Create Shipping " + Line);
            logger.info("Create Shipping : " + result);
            logger.info(Line + " Logger End Create Shipping " + Line);
            return ResponseHandler.generateResponse("successfully create shipping", HttpStatus.CREATED, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create shipping!");
        }
    }

    @Override
    public ResponseEntity<Object> updateShipping(ShippingRequestDTO shippingRequestDTO, Long id) throws Exception {
        try {
            Shipping shipping = shippingRepository.findById(id).orElseThrow(() -> new Exception("Shipping not found"));
            Optional<Shipping> check = shippingRepository.findByNameIgnoreCase(shippingRequestDTO.getName());
            if (check.isPresent()) {
                throw new Exception("this name not unique");
            }
            shipping.setShippingId(id);
            shipping.setName(shippingRequestDTO.getName());
            shipping.setPrice(shippingRequestDTO.getPrice());
            ShippingResponseDTO result = shippingRepository.save(shipping).convertToResponse();
            logger.info(Line + " Logger Start Update Shipping " + Line);
            logger.info("Update Shipping : " + result);
            logger.info(Line + " Logger End Update Shipping " + Line);
            return ResponseHandler.generateResponse("successfully updated Shipping", HttpStatus.CREATED, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not Found!");
        }
    }


    @Override
    public ResponseEntity<Object> deleteShipping(Long id) throws Exception {
        try {
            shippingRepository.findById(id).orElseThrow(() -> new Exception("Shipping not found"));
            shippingRepository.deleteById(id);
            logger.info(Line + " Logger Start Delete Shipping " + Line);
            logger.info("Success Delete Shipping by ID");
            logger.info(Line + " Logger End Delete Shipping " + Line);
            return ResponseHandler.generateResponse("successfully deleted shipping by id", HttpStatus.OK, "success delete");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found!");
        }
    }

    @Override
    public ResponseEntity<Object> findByName(String keyword) throws Exception {
        try {
            List<Shipping> shippings = shippingRepository.findByName(keyword);
            if (shippings.isEmpty()) {
                throw new Exception("Shipping not found");
            }
            List<ShippingResponseDTO> results = shippings.stream()
                    .map(Shipping::convertToResponse).toList();
            logger.info(Line + " Logger Start Get by Shipping Name " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + " Logger End Get  by Shipping Name " + Line);
            return ResponseHandler.generateResponse("successfully get name", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not Found!");
        }
    }
}
