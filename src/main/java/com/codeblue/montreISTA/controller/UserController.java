package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="1. User")
public class UserController {

    private UserService userService;

    //GET ALL
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUser() {
        try {
            List<User> users = userService.findAllUser();

            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    //CREATE
    @PostMapping("/users/create")
    public ResponseEntity<Object> createUser(@RequestBody User newUser) {
        try {
            User user = userService.createUser(newUser);
            return ResponseHandler.generateResponse("successfully retrieved user", HttpStatus.CREATED, user);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

//    //GET ALL BY User ID
//    @GetMapping("/users/{user_id}")
//    public ResponseEntity<Object> getAllUserByUserId(@PathVariable("user_id") Long userId) {
//        try {
//            Optional<User> user = userService.findUserById(userId);
//            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, user);
//        } catch (Exception e) {
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
//        }
//    }
//
//    //GET ONE BY ID
//    @GetMapping("/users/{id}")
//    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
//        try {
//            Optional<User> user = userService.findUserById(id);
//            return ResponseHandler.generateResponse("successfully retrieved user", HttpStatus.OK, user);
//        } catch (Exception e) {
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
//        }
//    }

    //UPDATE
//    @PutMapping("/users/update/{id}")
//    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
//        try {
//            Optional<User> targetUser = userService.findUserById(id);
//            User updateUser = targetUser.get();
//            updateUser.setUserId(id);
//            updateUser.setAddress(user.getName());
//            updateUser.setEmail(user.getEmail());
//            updateUser.setName(user.getName());
//            updateUser.setPassword(user.getPassword());
//            updateUser.setPhone(user.getPhone());
//            updateUser.setPhoto(user.getPhoto());
//            updateUser.setUsername(user.getUsername());
//
//            userService.updateUser(updateUser);
//            return ResponseHandler.generateResponse("successfully updated User", HttpStatus.CREATED, updateUser);
//        } catch (Exception e) {
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
//        }
//    }
//
//    //DELETE
//    @DeleteMapping("/users/delete/{id}")
//    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
//        try {
//            userService.deleteUser(id);
//            return ResponseHandler.generateResponse("successfully deleted User", HttpStatus.MULTI_STATUS, null);
//        } catch (Exception e) {
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
//        }
//    }
}
