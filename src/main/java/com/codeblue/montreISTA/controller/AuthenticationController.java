package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.JwtResponse;
import com.codeblue.montreISTA.DTO.LoginUserRequest;
import com.codeblue.montreISTA.DTO.RegistrationDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.security.JwtUtils;
import com.codeblue.montreISTA.security.MyUserDetails;
import com.codeblue.montreISTA.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Tag(name="00. Authentication")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private static final String Line = "====================";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    //CREATE

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginUserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            String jwt = jwtUtils.generateJwtToken(authentication);

            MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtResponse jwtResponse = new JwtResponse(jwt,userDetails.getUserId(),userDetails.getUsername(), userDetails.getEmail(),roles);
            logger.info(Line + "Logger Start Login " + Line);
            logger.info(String.valueOf(jwtResponse));
            logger.info(Line + "Logger End Login " + Line);
            return ResponseHandler.generateResponse("successfully login", HttpStatus.OK, jwtResponse);

        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Login!");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody RegistrationDTO registrationRequest) {
        try {
            if (userRepository.existsByUsername(registrationRequest.getUsername())){
                return ResponseHandler.generateResponse("Username is already taken!", HttpStatus.BAD_REQUEST, null);
            }
            if (userRepository.existsByEmail(registrationRequest.getUsername())){
                return ResponseHandler.generateResponse("Email is already in use!", HttpStatus.BAD_REQUEST, null);
            }

            UserResponseDTO result = userService.registrationUser(registrationRequest);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully registered! please login", HttpStatus.CREATED, result);

        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create user!");
        }
    }
}
