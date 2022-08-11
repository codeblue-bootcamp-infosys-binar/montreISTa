package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUsersUserUserId(Long id);
    Role findByRoleNameOrderByCreatedAt(String rolename);
}
