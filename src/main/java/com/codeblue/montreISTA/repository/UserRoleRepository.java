package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    List<UserRole> findByUserUserId(Long id);
    List<UserRole> findByRoleRoleNameIgnoreCase(String keyword);
}
