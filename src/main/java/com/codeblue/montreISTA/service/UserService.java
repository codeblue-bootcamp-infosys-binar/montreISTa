package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
@Autowired

     UserRepository userRepository;

    public List<User> findAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updateUser) {
        return userRepository.save(updateUser);
    }

    public void deleteUser(Long id) {
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
    public List<User> findByUsername(String keyword) {
        List<User> users = userRepository.findByUsername(keyword);
        return users;
    }
    public List<User> findByName(String keyword) {
        List<User> users = userRepository.findByName(keyword);
        return users;
    }
}