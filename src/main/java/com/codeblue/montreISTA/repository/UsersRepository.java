package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Long> {
}
