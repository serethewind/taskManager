package com.serethewind.taskmanager.repository;

import com.serethewind.taskmanager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where upper(u.username) = upper(?1) or upper(u.email) = upper(?2)")
    Optional<Boolean> existsByUsernameOrEmail(String username, String email);

    Optional<Boolean> existsByUsername(String username);

    UserEntity findByUsername(String username);

    @Query("select u from UserEntity u where upper(u.username) = upper(:query) or upper(u.email) = upper(:query)")
    Optional<UserEntity> findByUserNameOrEmailWithJPQL(String query);
}
