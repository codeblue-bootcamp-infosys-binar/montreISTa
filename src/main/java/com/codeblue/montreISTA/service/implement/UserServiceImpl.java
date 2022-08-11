package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.UserResponseDTO;
import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

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
        return  userRepository.findById(id).orElseThrow(()-> new Exception("User not found"))
                .convertToResponse(role);
    }

    @Override
    public UserResponseDTO createUser(User user) throws Exception {
        return null;
    }

    @Override
    public UserResponseDTO updateUser(User updateUser, Long id) throws Exception {
        return null;
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        userRepository.deleteById(id);
    }
}
