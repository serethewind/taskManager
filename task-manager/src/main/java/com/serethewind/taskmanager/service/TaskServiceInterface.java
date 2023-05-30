package com.serethewind.taskmanager.service;

import com.serethewind.taskmanager.dto.TaskRequest;
import com.serethewind.taskmanager.dto.TaskResponse;
import com.serethewind.taskmanager.entity.Task;

import java.util.List;

public interface TaskServiceInterface {

    List<TaskResponse> fetchAllTask();

    TaskResponse fetchTaskById(Long id);

    TaskResponse createSingleTask(TaskRequest taskRequest);

    TaskResponse updateTask(Long id, TaskRequest taskRequest);

    String deleteTask(Long id);

    List<Task> fetchTaskByQuery(String query);

    TaskResponse completeTask(Long id);

    TaskResponse inCompleteTask(Long id);
}
