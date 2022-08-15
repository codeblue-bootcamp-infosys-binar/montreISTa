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
        AtomicReference<UserResponseDTO> userDTOlambda = new AtomicReference<>(new UserResponseDTO());
        if(requestRole.isEmpty()) {
            userDTOlambda.set(this.addToRoleCreate(registrationDTO.convertToEntity()));
            }else {
            boolean check = requestRole.stream().anyMatch(r->r.contains("ROLE_ADMIN"));
            if(check){
                throw new Exception("User need admin for ROLE_ADMIN");
            }
            requestRole.forEach(String::toUpperCase);
            requestRole.forEach(role->{
                try{
                if(role.equals("ROLE_USER")){
                    userDTOlambda.set(this.addToRoleCreate(registrationDTO.convertToEntity()));
                }else {
                    {
                        throw new Exception("For now you can only have ROLE_USER, next patch we will upgrade");
                    }
                }
            } catch (Exception e) {
                    e.printStackTrace();
                }});
          };
        return userDTOlambda.get();
    }

    @Override
    public UserResponseDTO updateUser(RegistrationDTO registrationDTO, Authentication authentication) throws Exception {
        User userByUsername = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new Exception("User not found"));
        User user = registrationDTO.convertToEntity();
        user.setUserId(userByUsername.getUserId());
        List<String> requestRole = registrationDTO.getRoles();
        AtomicReference<UserResponseDTO> userDTOlambda = new AtomicReference<>(new UserResponseDTO());
        if(requestRole.isEmpty()) {
            userDTOlambda.set(this.addToRoleCreate(user));
        }else {
            boolean check = requestRole.stream().anyMatch(r->r.contains("ROLE_ADMIN"));
            if(check){
                throw new Exception("User need admin for ROLE_ADMIN");
            }
            requestRole.forEach(String::toUpperCase);
            requestRole.forEach(role->{
                try{
                    if(role.equals("ROLE_USER")){
                        userDTOlambda.set(this.addToRoleCreate(user));
                    }else {
                        {
                            throw new Exception("For now you can only have ROLE_USER, next patch we will upgrade");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }});
        };
        return userDTOlambda.get();
    }



    @Override
    public void deleteUser(Long id) throws Exception {
        userRepository.deleteById(id);
    }

    public void checkRole_user(User user)throws Exception{
        List<Role> roles = roleRepository.findByUsersUserUserId(user.getUserId());
        boolean check = roles.stream().anyMatch(role->role.getRoleName()
                .contains("ROLE_USER"));
        if(!check){
            UserRole addrole = new UserRole();
            Role role = roleRepository.findByRoleNameOrderByRoleIdDesc("ROLE_USER");
            addrole.setRole(role);
            addrole.setUser(user);
            userRoleRepository.save(addrole);
        }
    }
    public UserResponseDTO convertResponse(User userSave)throws Exception{
        List<Role> rolesUser = roleRepository.findByUsersUserUserId(userSave.getUserId());
        List<String> roleDTO = rolesUser.stream().map(Role::getRoleName).collect(Collectors.toList());
        return userSave.convertToResponse(roleDTO);
    }

    public UserResponseDTO addToRoleCreate(User user)throws Exception{
        User userSave = userRepository.save(user);
        this.checkRole_user(user);
        return this.convertResponse(userSave);
    }
}
