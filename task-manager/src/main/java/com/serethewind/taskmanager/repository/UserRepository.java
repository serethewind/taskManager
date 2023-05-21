package com.serethewind.taskmanager.repository;

import com.serethewind.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String userName);

    Optional<User> findByUsernameOrEmail(String userName, String email);
}
