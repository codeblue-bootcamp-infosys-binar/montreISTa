package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.RegistrationDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name="01. User")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService userService;


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

    @PostMapping(value="/user/upload-photo-profile",consumes ="multipart/form-data" )
    public ResponseEntity<Object> postPhotoProfile(@RequestParam ("file") MultipartFile file,
                                                   Authentication authentication) throws IOException {
        try{
            UserResponseDTO results = userService.uploadPhotoProfile(authentication,file);
            return ResponseHandler.generateResponse("Success upload photo profile",HttpStatus.OK,results);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"failed update photo");
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
