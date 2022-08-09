//package com.codeblue.montreISTA.repository;
//
//import com.codeblue.montreISTA.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface UserRepository extends JpaRepository<User,Long> {
//
//    @Query(value = "SELECT * FROM users s WHERE user_id=?1", nativeQuery = true)
//    List<User> findByUserId(Long id);
//    List<User> findByUsername(String keyword);
//    List<User> findByName(String keyword);
//
//}