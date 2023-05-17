package com.serethewind.taskmanager.controller;

import com.serethewind.taskmanager.dto.TaskRequest;
import com.serethewind.taskmanager.dto.TaskResponse;
import com.serethewind.taskmanager.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TaskController {
    @Autowired
    TaskServiceImpl taskServiceImpl;

    //get all tasks
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTask() {
        return new ResponseEntity<>(taskServiceImpl.fetchAllTask(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getSingleTask(@PathVariable("id") Long id) {
        return new ResponseEntity<>(taskServiceImpl.fetchTaskById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return new ResponseEntity<>(taskServiceImpl.createSingleTask(taskRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable("id") Long id, @RequestBody TaskRequest taskRequest) {
        return new ResponseEntity<>(taskServiceImpl.updateTask(id, taskRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteTaskById(@PathVariable("id") Long id) {
        return taskServiceImpl.deleteTask(id);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> setToComplete(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskServiceImpl.completeTask(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TaskResponse> setToInComplete(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskServiceImpl.inCompleteTask(id), HttpStatus.OK);
    }

}
