package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.DTO.UpdateUserDTO;
import com.codeblue.montreISTA.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@Tag(name="01. User")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/my-profile")
    public ResponseEntity<?> getMyProfile(Authentication authentication) throws Exception {
        return userService.findMyProfile(authentication.getName());
    }

    //UPDATE
    @PutMapping("/user/edit-profile")
    public ResponseEntity<Object> updateUser(@RequestBody UpdateUserDTO user, Authentication authentication) throws Exception {
          return userService.updateUser(user,authentication);
    }

    @PostMapping(value="/user/upload-photo-profile",consumes ="multipart/form-data" )
    public ResponseEntity<Object> postPhotoProfile(@RequestParam ("file") MultipartFile file,
                                                   Authentication authentication) throws Exception {
        return userService.uploadPhotoProfile(authentication,file);

    }

        //GET ALL
    @GetMapping("/dashboard/users")
    public ResponseEntity<Object> getAllUser() throws Exception {
       return userService.findAllUser();
    }

    //GET BY ID
    @GetMapping("/dashboard/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) throws Exception {
          return userService.findByUserId(id);
    }

    //DELETE
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id,Authentication authentication) throws Exception {
            return userService.deleteUser(id,authentication);
    }

}
