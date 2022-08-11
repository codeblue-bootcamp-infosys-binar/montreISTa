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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;

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
    public UserResponseDTO registrationUser(RegistrationDTO registrationDTO) throws Exception {
        List<String> requestRole = registrationDTO.getRoles();
        requestRole.forEach(String::toUpperCase);
        Boolean check = requestRole.stream().anyMatch(r->r.contains("ROLE_ADMIN"));
        if(check){
            throw new Exception("User need admin for ROLE_ADMIN");
        }
        User userSave = userRepository.save(registrationDTO.convertToEntity());
        requestRole.forEach(role ->{
            try {
          if(requestRole==null){
                  this.addToRole(userSave);
          }else if(role.equals("ROLE_USER")){
              this.addToRole(userSave);
          }else {
              { throw new Exception("For now you can only have ROLE_USER, next patch we will upgrade");
              }
          }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        List<Role> roles = roleRepository.findByUsersUserUserId(userSave.getUserId());
        List<String> role = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        return userSave.convertToResponse(role);
    }

    @Override
    public UserResponseDTO updateUser(User updateUser, Long id) throws Exception {
        return null;
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        userRepository.deleteById(id);
    }

    public void addToRole(User userSave)throws Exception{
        List<Role> roles = roleRepository.findByUsersUserUserId(userSave.getUserId());
        Boolean check = roles.stream().anyMatch(role->role.getRoleName()
                .contains("ROLE_USER"));
        if(check){
            throw new Exception("User have ROLE_USER");
        }
        UserRole addrole = new UserRole();
        Role role = roleRepository.findByRoleNameOrderByCreatedAt("ROLE_USER");
        addrole.setRole(role);
        addrole.setUser(userSave);
        userRoleRepository.save(addrole);
    }
}
