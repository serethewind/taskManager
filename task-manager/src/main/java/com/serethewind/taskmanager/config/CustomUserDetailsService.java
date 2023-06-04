package com.serethewind.taskmanager.config;

import com.serethewind.taskmanager.entity.UserEntity;
import com.serethewind.taskmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUserNameOrEmailWithJPQL(usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Username or email entered does not match any email in database"));
        return new CustomUserDetails(user);
    }
}
