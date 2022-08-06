package com.codeblue.montreISTA.service;

import DTO.UserResponseDTO;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    static
    UserRepository userRepository;

    public static List<User> findAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public static Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public static List<UserResponseDTO> findByUsername(String username) throws Exception {
        return null;
    }

    public static User createUser(User user) {
        return userRepository.save(user);
    }

    public static User updateUser(User updateUser) {
        return userRepository.save(updateUser);
    }

    public static void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findUserByUserId(Long id) {
        List<User> user = userRepository.findByUserId(id);
        if(user.isEmpty()){
            return null;
        } else {
            return user;
        }
    }
}
