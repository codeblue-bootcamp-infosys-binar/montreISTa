package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.UserRepository;
import com.codeblue.montreISTA.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> findAllUser() {
        return userRepository.findAllByOrderByUserIdAsc();
    }


    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User updateUser) {
        return userRepository.save(updateUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public User findByUserId(Long id) throws Exception{
        return userRepository.findById(id).orElseThrow(()->new Exception("User not found"));
    }

    public User findByUsername(String keyword) throws Exception{

        return userRepository.findByUsername(keyword).orElseThrow(()->new Exception("User not found"));
    }
    public List<User> findByName(String keyword) {
        List<User> users = userRepository.findByName(keyword);
        return users;
    }
}
