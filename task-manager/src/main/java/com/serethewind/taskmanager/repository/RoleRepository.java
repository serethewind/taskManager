package com.serethewind.taskmanager.repository;

import com.serethewind.taskmanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName (String roleName);
}
