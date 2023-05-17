package com.serethewind.taskmanager.service.impl;

import com.serethewind.taskmanager.dto.TaskRequest;
import com.serethewind.taskmanager.dto.TaskResponse;
import com.serethewind.taskmanager.entity.Task;
import com.serethewind.taskmanager.exception.ResourceNotFoundException;
import com.serethewind.taskmanager.repository.TaskRepository;
import com.serethewind.taskmanager.service.TaskServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskServiceInterface {


    private TaskRepository taskRepository;

    private ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TaskResponse> fetchAllTask() {
        List<Task> taskList = taskRepository.findAll();
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for (Task task : taskList) {
//            TaskResponse taskResponse1 = new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isCompleted());
            TaskResponse taskResponse = modelMapper.map(task, TaskResponse.class);
            taskResponseList.add(taskResponse);
        }
        return taskResponseList;
    }

    @Override
    public TaskResponse fetchTaskById(Long id) {
        Task foundTask = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));
        return modelMapper.map(foundTask, TaskResponse.class);
    }

    @Override
    public TaskResponse createSingleTask(TaskRequest taskRequest) {
        Task task = modelMapper.map(taskRequest, Task.class);
        taskRepository.save(task);
        return modelMapper.map(task, TaskResponse.class);
    }


    @Override
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        taskRepository.save(task);
        return modelMapper.map(task, TaskResponse.class);
    }

    @Override
    public String deleteTask(Long id) {
        String response;
        boolean status = taskRepository.existsById(id);
        if (true) {
//            Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            taskRepository.deleteById(id);
            response = "User with id " + id + " has been successfully deleted";
        } else {
            response = "Id does not match any user in our database.";
        }
        return response;
    }

    @Override
    public TaskResponse completeTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setCompleted(true);
        taskRepository.save(task);
        return modelMapper.map(task, TaskResponse.class);
    }

    @Override
    public TaskResponse inCompleteTask(Long id) {
       Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
       task.setCompleted(false);
       taskRepository.save(task);
       return modelMapper.map(task, TaskResponse.class);
    }


}

//    private TaskRequest mapToDto(Task task) {
//        TaskRequest taskRequest = new TaskRequest();
//        taskRequest.setName(task.getName());
//        taskRequest.setCompleted(task.isCompleted());
//        return taskRequest;
//    }
//
//    private TaskRequest mapToDto(Task task, TaskRequest taskRequest){
//        task.setName(taskRequest.getName());
//        task.setCompleted(taskRequest.isCompleted());
//        taskRepository.save(task);
//        return mapToDto(task);
//    }

