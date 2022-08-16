package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.LoginUserRequest;
import com.codeblue.montreISTA.DTO.RegistrationDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name="00. Authentication")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private static final String Line = "====================";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginUserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserResponseDTO user = userService.findByUsername(authentication.getName());
            logger.info(Line + "Logger Start Login " + Line);
            logger.info(String.valueOf(user));
            logger.info(Line + "Logger End Login " + Line);
            return ResponseHandler.generateResponse("successfully login", HttpStatus.OK, user);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Login!");
        }
    }

    //CREATE
    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody RegistrationDTO newUser) {
        try {
            UserResponseDTO user = userService.registrationUser(newUser);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(user));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully retrieved user", HttpStatus.CREATED, user);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create user!");
        }
    }
}
