package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.controller.AuthenticationController;
import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.entity.UserRole;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.repository.UserRoleRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.security.JwtUtils;
import com.codeblue.montreISTA.security.MyUserDetails;
import com.codeblue.montreISTA.service.UserService;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private static final String Line = "====================";

    @Override
    public ResponseEntity<Object> authenticationUser(LoginUserRequest userRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername(), userDetails.getEmail(), roles);

            logger.info(Line + "Logger Start Login " + Line);
            logger.info(String.valueOf(jwtResponse));
            logger.info(Line + "Logger End Login " + Line);

            return ResponseHandler.generateResponse("successfully login", HttpStatus.OK, jwtResponse);

        } catch (Exception e) {

            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);

            return ResponseHandler.generateResponse("Username or Password is incorrect", HttpStatus.BAD_REQUEST, "Failed Login!");
        }
    }

    @Override
    public ResponseEntity<Object> findAllUser() {

        try {
            List<User> users = userRepository.findAllByOrderByUserIdAsc();
            List<UserResponseDTO> usersDTO = new ArrayList<>();
            logger.info("==================== Logger Start Get All Users     ====================");
            for (User user : users) {
                List<Role> roles = roleRepository.findByUsersUserUserId(user.getUserId());
                List<String> role = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
                UserResponseDTO userDTO = user.convertToResponse(role);
                usersDTO.add(userDTO);
                logger.info("-------------------------");
                logger.info("User ID    : " + user.getUserId());
                logger.info("Username   : " + user.getUsername());
                logger.info("Name       : " + user.getName());
                logger.info("Email      : " + user.getEmail());
                logger.info("Address    : " + user.getAddress());
                logger.info("Phone      : " + user.getPhone());
                logger.info("Photo      : " + user.getPhoto());
                logger.info("Role       : " + role);
            }
            logger.info("==================== Logger End Get All Users    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, usersDTO);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Get All Users");
        }
    }

    @Override
    public ResponseEntity<Object> findByUserId(Long id)  {

        try{

            List<Role> roles = roleRepository.findByUsersUserUserId(id);
            List<String> role = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
            UserResponseDTO user =  userRepository.findById(id)
                    .orElseThrow(() -> new Exception("User not found"))
                    .convertToResponse(role);

            logger.info(Line + "Logger Start find By Id " + Line);
            logger.info(String.valueOf(user));
            logger.info(Line + "Logger End find By Id " + Line);

            return ResponseHandler.generateResponse("Successfully Retrieved User", HttpStatus.OK, user);

        } catch (Exception e) {

            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Find by Id");
        }
    }

    @Override
    public ResponseEntity<Object> findMyProfile(String keyword) throws Exception {

        try {

            List<Role> roles = roleRepository.findByUsersUserUsername(keyword);
            List<String> role = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
            UserResponseDTO result = userRepository.findByUsername(keyword).orElseThrow(() -> new Exception("User not found"))
                    .convertToResponse(role);

            logger.info(Line + "Logger Start findMyProfile " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End findMyProfile " + Line);

            return ResponseHandler.generateResponse("successfully retrieved users", HttpStatus.OK, result);

        } catch (Exception e) {

            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed find My Profile");
        }
    }


    @Override
    public ResponseEntity<Object> registrationUser(RegistrationDTO registrationDTO)  {

        try {

            if (userRepository.existsByUsername(registrationDTO.getUsername())) {
                throw new Exception("Username is already in use");
            }

            if (userRepository.existsByEmail(registrationDTO.getEmail())) {
                throw new Exception("Email is already in use");
            }

            String phone = registrationDTO.getPhone();
            String phonePattern = "^(?:\\+62|\\(0\\d{2,3}\\)|0)\\s?(?:361|8[17]\\s?\\d?)?(?:[ -]?\\d{3,4}){2,3}$";
            if (!phone.matches(phonePattern)){
                throw new Exception("please use the correct phone number format");
            }

            String password = registrationDTO.getPassword();
            String passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
            if (!password.matches(passPattern)){
                throw new Exception("please enter the right password format");
            }

            User user = registrationDTO.convertToEntity();
            user.setPhoto("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User userSave = userRepository.save(user);
            this.newRole(userSave);

            UserResponseDTO result = this.convertResponse(userSave);

            logger.info(Line + "Logger Start Registration " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Registration " + Line);

            return ResponseHandler.generateResponse("successfully registered! please login", HttpStatus.CREATED, result);

        }catch (Exception e){

            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create user!");
        }
    }

    @Override
    public ResponseEntity<Object> updateUser(UpdateUserDTO updateUserDTO, Authentication authentication)  {

        try {

            User userByUsername = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("User not found"));
            User user = updateUserDTO.convertToEntity();

            user.setUserId(userByUsername.getUserId());
            user.setPhoto(userByUsername.getPhoto());

            List<String> requestRole = updateUserDTO.getRoles();
            this.checkRole(requestRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User userSave = userRepository.save(user);
            this.addRole(requestRole,userSave);

            UserResponseDTO results = convertResponse(userSave);

            logger.info(Line + "Logger Start Update Profile" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Update Profile" + Line);

            return ResponseHandler.generateResponse("Success upload photo profile",HttpStatus.OK,results);

        }catch (Exception e){

            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"failed upload photo");
        }
    }

    @Override

    public ResponseEntity<Object> uploadPhotoProfile(Authentication authentication, MultipartFile file)  {

        try{

            User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("Please sign up"));
            
            String url = cloudinaryService.uploadFile(file);
            user.setPhoto(url);
            User userSave = userRepository.save(user);
            UserResponseDTO results = this.convertResponse(userSave);

            logger.info(Line + "Logger Start Upload Photo Profile" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Upload Photo Profile" + Line);

            return ResponseHandler.generateResponse("Success upload photo profile",HttpStatus.OK,results);

        }catch (Exception e){

            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"failed update photo");
        }

    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id,Authentication authentication)  {

        try{
            User user = userRepository.findById(id).orElseThrow(()->new Exception("User not found"));
            
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());

            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkUser = user.getUsername().equals(authentication.getName());

            if (checkRoles || checkUser){
                userRepository.deleteById(id);
            } else {
                throw new Exception("You can't delete other user");
            }

        return ResponseHandler.generateResponse("successfully deleted User", HttpStatus.MULTI_STATUS, "Success Delete");

    } catch (Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Delete User");
        }
    }

    public void checkRole(List<String> requestRole){
        requestRole.forEach(role-> {
            try {
                roleRepository.findByRoleNameIgnoreCase(role).orElseThrow(() -> new Exception("Role not found"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public UserResponseDTO convertResponse(User userSave){
        List<Role> rolesUser = roleRepository.findByUsersUserUserId(userSave.getUserId());
        List<String> roleDTO = rolesUser.stream().map(Role::getRoleName).collect(Collectors.toList());
        return userSave.convertToResponse(roleDTO);
    }

    public void newRole(User user) throws Exception {
    
        Role roleGet = roleRepository.findByRoleNameIgnoreCase("ROLE_USER").orElseThrow(()->new Exception("Role not found"));
        UserRole addRole = new UserRole();

        addRole.setRole(roleGet);
        addRole.setUser(user);

        userRoleRepository.save(addRole);
    }

    public void addRole(List<String> requestRole, User user) throws Exception {
        if(requestRole.isEmpty()) {

            Role roleUser = roleRepository.findByRoleNameIgnoreCase("ROLE_USER").orElseThrow(()->new Exception("Role not found"));
            UserRole newRole = new UserRole();
            newRole.setRole(roleUser);
            newRole.setUser(user);
            userRoleRepository.save(newRole);

        } else {

            boolean check = requestRole.stream().anyMatch(role->role.contains("ROLE_ADMIN"));
            if(check){
                throw new Exception("User need admin for ROLE_ADMIN");
            }

            requestRole.forEach(role->{

                try{
                    List<UserRole> userRoles = userRoleRepository.findByRoleRoleNameIgnoreCase(role);
                    boolean checkUser = userRoles.stream().anyMatch(userRole -> Objects.equals(userRole.getUser().getUserId(), user.getUserId()));

                    if(!checkUser){
                        Role roleGet = roleRepository.findByRoleNameIgnoreCase(role).orElseThrow(()->new Exception("Role not found"));
                        UserRole addRole = new UserRole();
                        addRole.setRole(roleGet);
                        addRole.setUser(user);
                        userRoleRepository.save(addRole);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
