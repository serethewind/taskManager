package com.serethewind.taskmanager.controller;

import com.serethewind.taskmanager.dto.AuthLoginDto;
import com.serethewind.taskmanager.dto.AuthRegisterDto;
import com.serethewind.taskmanager.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRegisterDto authRegisterDto){
        return new ResponseEntity<>(authService.registerUser(authRegisterDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthLoginDto authLoginDto){
        return new ResponseEntity<>(authService.loginUser(authLoginDto), HttpStatus.OK);
    }
}
