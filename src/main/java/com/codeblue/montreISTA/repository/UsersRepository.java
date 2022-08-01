package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
}
