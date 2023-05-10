package com.serethewind.taskmanager.service.impl;

import com.serethewind.taskmanager.dto.TaskRequest;
import com.serethewind.taskmanager.entity.Task;
import com.serethewind.taskmanager.repository.TaskRepository;
import com.serethewind.taskmanager.service.TaskServiceInterface;
import jdk.internal.org.jline.utils.ShutdownHooks;
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
        for (Task task : taskList){
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
       taskRepository.save(task);
       return mapToDto(task);
    }

    @Override
    public TaskRequest updateTask(Long id, TaskRequest taskRequest) {
        taskRepository.findById(id)
    }

    @Override
    public String deleteTask(Long id) {
        return null;
    }

    private TaskRequest mapToDto(Task task){
        TaskRequest taskRequest = new TaskRequest(task.getName(), task.isCompleted());
        return taskRequest;
    }
}
