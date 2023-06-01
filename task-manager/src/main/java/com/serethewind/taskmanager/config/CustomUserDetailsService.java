package com.serethewind.taskmanager.config;

import com.serethewind.taskmanager.entity.User;
import com.serethewind.taskmanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        //load user from the database using the custom method created in the jpa repository
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Username or email entered does not match any email in database"));

        //user role is converted to GrantedAuthority. A set is used because the roles are also stored as sets.
//        Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

//        return new org.springframework.security.core.userdetails.User(
//                usernameOrEmail,
//                user.getPassword(),
//                grantedAuthorities
//        );

        return new CustomUserDetails(user);
    }
}
