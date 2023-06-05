package com.serethewind.taskmanager.controller;

import com.serethewind.taskmanager.dto.CustomTaskResponse;
import com.serethewind.taskmanager.dto.TaskRequest;
import com.serethewind.taskmanager.dto.TaskResponse;
import com.serethewind.taskmanager.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    @Autowired
    TaskServiceImpl taskServiceImpl;

    //get all tasks
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<TaskResponse> getSingleTask(@PathVariable("id") Long id) {
        return new ResponseEntity<>(taskServiceImpl.fetchTaskById(id), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
//    @GetMapping
//    public ResponseEntity<List<TaskResponse>> getAllTask() {
//        return new ResponseEntity<>(taskServiceImpl.fetchAllTask(), HttpStatus.OK);
//    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<CustomTaskResponse> getAllTask(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return new ResponseEntity<>(taskServiceImpl.fetchAllTask(pageNo, pageSize), HttpStatus.OK);
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

}
