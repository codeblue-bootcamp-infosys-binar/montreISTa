package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.LoginUserRequest;
import com.codeblue.montreISTA.DTO.RegistrationDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="01. User")
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginUserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserResponseDTO user = userService.findByUsername(authentication.getName());
            return ResponseHandler.generateResponse("successfully login", HttpStatus.OK, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody RegistrationDTO newUser) {
        try {
            UserResponseDTO user = userService.registrationUser(newUser);
            return ResponseHandler.generateResponse("successfully retrieved user", HttpStatus.CREATED, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //UPDATE
    @PutMapping("/user/editProfile")
    public ResponseEntity<Object> updateUser(@RequestBody RegistrationDTO user,Authentication authentication) {
        try {
           UserResponseDTO updateUser = userService.updateUser(user,authentication);
            return ResponseHandler.generateResponse("successfully updated User", HttpStatus.CREATED, updateUser);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    //GET ALL
    @GetMapping("/dashboard/users")
    public ResponseEntity<Object> getAllUser() {
        try {
            List<UserResponseDTO> users = userService.findAllUser();

            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET BY ID
    @GetMapping("/dashboard/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        try {
            UserResponseDTO user = userService.findByUserId(id);
            return ResponseHandler.generateResponse("Successfully Retrieved User", HttpStatus.OK, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/dashboard/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseHandler.generateResponse("successfully deleted User", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }



}
