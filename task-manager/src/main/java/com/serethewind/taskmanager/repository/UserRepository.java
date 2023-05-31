package com.serethewind.taskmanager.repository;

import com.serethewind.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByUsername(String username);

//    User findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String userName, String email);

    @Query("select u from User u where upper(u.username) = upper(:query) or upper(u.email) = upper(:query)")
    Optional<User> findByUserNameOrEmailWithJPQL(String query);
}
