package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.RegistrationDTO;
import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.entity.UserRole;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.repository.UserRoleRepository;
import com.codeblue.montreISTA.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final CloudinaryService cloudinaryService;


    @Override
    public List<UserResponseDTO> findAllUser() {
        List<User> users = userRepository.findAllByOrderByUserIdAsc();
        List<UserResponseDTO> usersDTO= new ArrayList<>();
        for(User user:users){
            List<Role> roles = roleRepository.findByUsersUserUserId(user.getUserId());
            List<String> role = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
            UserResponseDTO userDTO = user.convertToResponse(role);
            usersDTO.add(userDTO);
        }
        return usersDTO;

    }

    @Override
    public UserResponseDTO findByUserId(Long id) throws Exception {
        List<Role> roles = roleRepository.findByUsersUserUserId(id);
        List<String> role = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        return userRepository.findById(id).orElseThrow(()-> new Exception("User not found"))
                .convertToResponse(role);
    }

    @Override
    public UserResponseDTO findByUsername(String keyword) throws Exception {
        List<Role> roles = roleRepository.findByUsersUserUsername(keyword);
        List<String> role = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        return userRepository.findByUsername(keyword).orElseThrow(()-> new Exception("User not found"))
                .convertToResponse(role); }

    @Override
    public UserResponseDTO registrationUser(RegistrationDTO registrationDTO) throws Exception {
        List<String> requestRole = registrationDTO.getRoles();
        User user = registrationDTO.convertToEntity();
        user.setPhoto("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
        this.checkRole(requestRole);

        User userSave = userRepository.save(user);
        //addRole
        this.addRole(requestRole,userSave);

        return this.convertResponse(userSave);
    }

    @Override
    public UserResponseDTO updateUser(RegistrationDTO registrationDTO, Authentication authentication) throws Exception {
        User userByUsername = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("User not found"));
        User user = registrationDTO.convertToEntity();
        user.setUserId(userByUsername.getUserId());
        List<String> requestRole = registrationDTO.getRoles();

        this.checkRole(requestRole);

        User userSave = userRepository.save(user);
                //addRole
        this.addRole(requestRole,userSave);

        return this.convertResponse(userSave);
    }

    @Override
    public UserResponseDTO uploadPhotoProfile(Authentication authentication, MultipartFile file) throws Exception {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("Please sign up"));
        String url = cloudinaryService.uploadFile(file);
        user.setPhoto(url);
        User userSave = userRepository.save(user);
        return this.convertResponse(userSave);
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        userRepository.deleteById(id);
    }
    public void checkRole(List<String> requestRole){
        requestRole.forEach(role-> {
            try {
                roleRepository.findByRoleNameIgnoreCase(role).orElseThrow(() -> new Exception("Role not found"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }}
        );
    }

    public UserResponseDTO convertResponse(User userSave)throws Exception{
        List<Role> rolesUser = roleRepository.findByUsersUserUserId(userSave.getUserId());
        List<String> roleDTO = rolesUser.stream().map(Role::getRoleName).collect(Collectors.toList());
        return userSave.convertToResponse(roleDTO);
    }


    public void addRole(List<String> requestRole, User user)throws Exception{
        if(requestRole.isEmpty()) {
            Role roleGet = roleRepository.findByRoleNameIgnoreCase("ROLE_USER").orElseThrow(()->new Exception("Role not found"));
            UserRole addRole = new UserRole();
            addRole.setRole(roleGet);
            addRole.setUser(user);
            userRoleRepository.save(addRole);
        }else {
            boolean check = requestRole.stream().anyMatch(r->r.contains("ROLE_ADMIN"));
            if(check){
                throw new Exception("User need admin for ROLE_ADMIN");
            }
            requestRole.forEach(role->{
                try{
                    Role roleGet = roleRepository.findByRoleNameIgnoreCase(role).orElseThrow(()->new Exception("Role not found"));
                    UserRole addRole = new UserRole();
                    addRole.setRole(roleGet);
                    addRole.setUser(user);
                    userRoleRepository.save(addRole);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }});
        }
    }
}
