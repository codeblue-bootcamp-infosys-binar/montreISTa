package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public List<User> findAllUser();

    public Optional<User> findUserById(Long id);

    public User createUser(User user);

    public User updateUser(User updateUser);

    public void deleteUser(Long id);

    public List<User> findUserByUserId(Long id) ;
    public List<User> findByUsername(String keyword) ;
    public List<User> findByName(String keyword) ;
}