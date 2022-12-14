package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUsersUserUserId(Long id);
    List<Role> findByUsersUserUsername(String keyword);
    Role findByRoleNameOrderByRoleIdDesc(String rolename);

    Optional<Role> findByRoleNameIgnoreCase(String rolename);
}
