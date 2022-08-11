package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="01. User")
public class UserController {

    private UserService userService;

    //GET ALL
    @GetMapping("/dashboard/users")
    public ResponseEntity<Object> getAllUser() {
        try {
            List<User> users = userService.findAllUser();

            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET BY NAME
    @GetMapping("/users/name")
    public ResponseEntity<Object> findByName(@Param("keyword") String keyword){
        try{
            List<User> users = userService.findByName(keyword);

            return ResponseHandler.generateResponse("successfully retrieved username", HttpStatus.OK, users);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    //GET BY USERNAME
    @GetMapping("/users/username")
    public ResponseEntity<Object> findByUsername(@Param("keyword") String keyword){
        try{
            User users = userService.findByUsername(keyword);

            return ResponseHandler.generateResponse("successfully retrieved username", HttpStatus.OK, users);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //GET ONE BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.findByUserId(id);
            return ResponseHandler.generateResponse("Successfully Retrieved User", HttpStatus.OK, user);
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


    //UPDATE
    @PutMapping("/users/update/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        try {
           User updateUser = userService.findByUserId(id);
            updateUser.setUserId(id);
            updateUser.setAddress(user.getName());
            updateUser.setEmail(user.getEmail());
            updateUser.setName(user.getName());
            updateUser.setPassword(user.getPassword());
            updateUser.setPhone(user.getPhone());
            updateUser.setPhoto(user.getPhoto());
            updateUser.setUsername(user.getUsername());

            userService.updateUser(updateUser);
            return ResponseHandler.generateResponse("successfully updated User", HttpStatus.CREATED, updateUser);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //DELETE
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseHandler.generateResponse("successfully deleted User", HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
