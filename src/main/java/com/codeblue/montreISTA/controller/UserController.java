package com.codeblue.montreISTA.controller;

import DTO.UserResponseDTO;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
public class UserController {

@Autowired
UserService userService;


    //GET ALL
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUser() {
        try {
            List<User> users = UserService.findAllUser();

            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ALL BY User ID
    @GetMapping("/users/{user_id}")
    public ResponseEntity<Object> getAllUserByUserId(@PathVariable("user_id") Long userId) {
        try {
            Optional<User> user = UserService.findUserById(userId);
            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    //GET BY USER.USERNAME
    @GetMapping("/users/username")
    public ResponseEntity<Object> findByUsername(@Param("keyword") String keyword){
        try {
            List<UserResponseDTO> results = UserService.findByUsername(keyword);
            return ResponseHandler.generateResponse("Succesfully Retrieved User", HttpStatus.OK, results);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        try {
            Optional<User> user = UserService.findUserById(id);
            return ResponseHandler.generateResponse("Successfully Retrieved User", HttpStatus.OK, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //CREATE
    @PostMapping("/users/create")
    public ResponseEntity<Object> createUser(@RequestBody User newUser) {
        try {
            User user = UserService.createUser(newUser);
            return ResponseHandler.generateResponse("successfully retrieved user", HttpStatus.CREATED, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //UPDATE
    @PutMapping("/users/update/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        try {
            Optional<User> targetUser = UserService.findUserById(id);
            User updateUser = targetUser.get();
            updateUser.setUserId(id);
            updateUser.setAddress(user.getName());
            updateUser.setEmail(user.getEmail());
            updateUser.setName(user.getName());
            updateUser.setPassword(user.getPassword());
            updateUser.setPhone(user.getPhone());
            updateUser.setPhoto(user.getPhoto());
            updateUser.setUsername(user.getUsername());

            UserService.updateUser(updateUser);
            return ResponseHandler.generateResponse("successfully updated User", HttpStatus.CREATED, updateUser);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        try {
            UserService.deleteUser(id);
            return ResponseHandler.generateResponse("successfully deleted User", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
