package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.BuyerResponseDTO;
import com.codeblue.montreISTA.controller.BuyerController;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.BuyerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);
    private static final String Line = "====================";

    private final BuyerRepository buyerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<Object> findAllBuyer() {
        try {
            List<BuyerResponseDTO> buyer = buyerRepository.findAllByOrderByBuyerIdAsc().stream().map(Buyer::convertToResponse)
                    .collect(Collectors.toList());
            logger.info("==================== Logger Start Get All Buyers     ====================");
            for (BuyerResponseDTO buyerData : buyer) {
                Map<String, Object> buyers = new HashMap<>();
                logger.info("-------------------------");
                logger.info("Buyer ID    : " + buyerData.getBuyer_id());
                logger.info("User ID     : " + buyerData.getUser_id());
            }
            logger.info("==================== Logger End Get All Buyers     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully get all buyers", HttpStatus.OK, buyer);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Buyers    ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Buyer had no value!")));
            logger.info("==================== Logger End Get All Buyers    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Buyer had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findBuyerById(Long id) throws Exception {
        try {
            BuyerResponseDTO buyers = buyerRepository.findById(id).orElseThrow(() -> new Exception("Buyer not found")).convertToResponse();
            logger.info(Line + "Logger Start Get buyer id " + Line);
            logger.info(String.valueOf(buyers));
            logger.info(Line + "Logger End Get buyer id " + Line);
            return ResponseHandler.generateResponse("successfully get buyer id", HttpStatus.OK, buyers);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Buyer id no data!");
        }
    }

    @Override
    public ResponseEntity<Object> createBuyer(Authentication authentication) throws Exception {
        try {
            User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new Exception("User not found"));
            Optional<Buyer> buyerUser = buyerRepository.findByUserUsername(authentication.getName());
            if (buyerUser.isEmpty()) {
                Buyer buyer = new Buyer();
                buyer.setUser(user);
                buyerRepository.save(buyer);
            }
            List<Product> products = productRepository.findAllByOrderByCreatedAtAsc();
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(user));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully login as buyer", HttpStatus.CREATED, user);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create buyer!");
        }
    }


    @Override
    public ResponseEntity<Object> deleteBuyer(Long id, Authentication authentication) throws Exception {
        try {
            Buyer buyer = buyerRepository.findById(id).orElseThrow(() -> new Exception("Buyer not found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkUser = buyer.getUser().getUsername().equals(authentication.getName());
            if (checkRoles || checkUser) {
                buyerRepository.deleteById(id);
            } else {
                throw new Exception("You can't delete other buyer");
            }
            buyerRepository.deleteById(id);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted buyer", HttpStatus.OK, "Success delete buyer");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete buyer!");
        }
    }

    @Override
    public ResponseEntity<Object> findByUsername(String keyword) throws Exception {
        try {
            List<BuyerResponseDTO> results = buyerRepository.findByUserUsername(keyword).stream().map(Buyer::convertToResponse)
                    .collect(Collectors.toList());
            if (results.isEmpty()) {
                throw new Exception("Buyer not found");
            }
            logger.info(Line + "Logger Start Get buyer username " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get buyer username " + Line);
            return ResponseHandler.generateResponse("successfully get buyer username", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Buyer no data!");
        }
    }
}