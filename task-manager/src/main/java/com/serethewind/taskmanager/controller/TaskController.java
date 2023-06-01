package com.serethewind.taskmanager.controller;

import com.serethewind.taskmanager.dto.*;
import com.serethewind.taskmanager.service.JwtService;
import com.serethewind.taskmanager.service.impl.JwtServiceImpl;
import com.serethewind.taskmanager.service.impl.TaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {
    private TaskServiceImpl taskServiceImpl;

    private JwtServiceImpl jwtServiceImpl;

    private AuthenticationManager authenticationManager;


    @GetMapping("/all")
    public String getToHomePage() {
        return "Home page accessed by all";
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(taskServiceImpl.registerUser(userRequest), HttpStatus.OK);
    }
    //get all tasks

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<TaskResponse> getSingleTask(@PathVariable("id") Long id) {
        return new ResponseEntity<>(taskServiceImpl.fetchTaskById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTask() {
        return new ResponseEntity<>(taskServiceImpl.fetchAllTask(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable("id") Long id, @RequestBody TaskRequest taskRequest) {
        return new ResponseEntity<>(taskServiceImpl.updateTask(id, taskRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return new ResponseEntity<>(taskServiceImpl.createSingleTask(taskRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteTaskById(@PathVariable("id") Long id) {
        return taskServiceImpl.deleteTask(id);
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<TaskResponse> setToComplete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(taskServiceImpl.completeTask(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/incomplete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<TaskResponse> setToInComplete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(taskServiceImpl.inCompleteTask(id), HttpStatus.OK);
    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtServiceImpl.generateToken(jwtAuthRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}
