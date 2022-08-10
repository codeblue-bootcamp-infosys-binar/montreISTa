package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByUsername(String keyword);
    List<User> findByName(String keyword);
    List<User> findAllByOrderByUserIdAsc();
}