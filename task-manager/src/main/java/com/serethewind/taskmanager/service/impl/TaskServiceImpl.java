package com.serethewind.taskmanager.service.impl;

import com.serethewind.taskmanager.dto.TaskRequest;
import com.serethewind.taskmanager.entity.Task;
import com.serethewind.taskmanager.repository.TaskRepository;
import com.serethewind.taskmanager.service.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskServiceInterface {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<TaskRequest> fetchAllTask() {
        List<Task> taskList = taskRepository.findAll();
        List<TaskRequest> taskRequestList = new ArrayList<>();
        for (Task task : taskList) {
            TaskRequest taskRequest = new TaskRequest(task.getName(), task.isCompleted());
            taskRequestList.add(taskRequest);
        }
        return taskRequestList;
    }

    @Override
    public TaskRequest fetchTaskById(Long id) {
        Task foundTask = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapToDto(foundTask);
    }

    @Override
    public TaskRequest createSingleTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setCompleted(taskRequest.isCompleted());
        taskRepository.save(task);
        return mapToDto(task, taskRequest);
    }



    @Override
    public TaskRequest updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        task.setName(taskRequest.getName());
        task.setCompleted(taskRequest.isCompleted());
        taskRepository.save(task);
        return mapToDto(task, taskRequest);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        taskRepository.delete(task);
    }

    private TaskRequest mapToDto(Task task) {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName(task.getName());
        taskRequest.setCompleted(task.isCompleted());
        return taskRequest;
    }

    private TaskRequest mapToDto(Task task, TaskRequest taskRequest){
        task.setName(taskRequest.getName());
        task.setCompleted(taskRequest.isCompleted());
        taskRepository.save(task);
        return mapToDto(task);
    }
}
