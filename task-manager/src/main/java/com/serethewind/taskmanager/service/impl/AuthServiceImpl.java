package com.serethewind.taskmanager.service.impl;

import com.serethewind.taskmanager.config.JwtGenerator;
import com.serethewind.taskmanager.dto.AuthLoginDto;
import com.serethewind.taskmanager.dto.AuthRegisterDto;
import com.serethewind.taskmanager.dto.AuthResponseDto;
import com.serethewind.taskmanager.entity.Role;
import com.serethewind.taskmanager.entity.UserEntity;
import com.serethewind.taskmanager.repository.RoleRepository;
import com.serethewind.taskmanager.repository.UserRepository;
import com.serethewind.taskmanager.service.AuthServiceInterface;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthServiceInterface {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private JwtGenerator jwtGenerator;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public String registerUser(AuthRegisterDto authRegisterDto) {
        //check if username exists. if not, create user.

        if (!userRepository.existsByUsername(authRegisterDto.getUsername()).get()) {
            Role roles = roleRepository.findByName("USER");

            UserEntity user = UserEntity.builder()
                    .fullname(authRegisterDto.getFullname())
                    .username(authRegisterDto.getUsername())
                    .email(authRegisterDto.getEmail())
                    .password(passwordEncoder.encode(authRegisterDto.getPassword()))
                    .roles(Collections.singleton(roles))
                    .build();

            userRepository.save(user);

            return "User successfully registered";

        } else {
            return "Username or Email is taken!";
        }
    }

    @Override
    public AuthResponseDto loginUser(AuthLoginDto authLoginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authLoginDto.getUsername(), authLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new AuthResponseDto(token);
    }
}
