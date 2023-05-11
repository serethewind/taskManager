package com.serethewind.taskmanager.service;

import com.serethewind.taskmanager.dto.TaskRequest;

import java.util.List;

public interface TaskServiceInterface {

    List<TaskRequest> fetchAllTask();

    TaskRequest fetchTaskById(Long id);

    TaskRequest createSingleTask(TaskRequest taskRequest);

    TaskRequest updateTask(Long id, TaskRequest taskRequest);

    void deleteTask(Long id);
}
