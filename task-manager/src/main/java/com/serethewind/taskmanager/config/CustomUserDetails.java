package com.serethewind.taskmanager.config;

import com.serethewind.taskmanager.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String name;
    private String password;
    private Set<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        name = user.getUsername();
        password = user.getPassword();
//        authorities = user.getRoles().stream().map((role -> new SimpleGrantedAuthority(role.name()))).collect(Collectors.toSet());
        authorities = Arrays.stream(user.getRoles().split(",")).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet());

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
