package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

//    @Query(value = "SELECT * FROM users s WHERE user_id=?1", nativeQuery = true)
//    List<User> findByUserId(Long id);
    Optional<User> findByUsername(String keyword);
    List<User> findByName(String keyword);
    List<User> findAllByOrderByUserIdAsc();

}