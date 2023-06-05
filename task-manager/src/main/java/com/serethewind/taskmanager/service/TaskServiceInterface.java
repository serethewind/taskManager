package com.serethewind.taskmanager.service;

import com.serethewind.taskmanager.dto.CustomTaskResponse;
import com.serethewind.taskmanager.dto.TaskRequest;
import com.serethewind.taskmanager.dto.TaskResponse;

import java.util.List;

public interface TaskServiceInterface {

//    List<TaskResponse> fetchAllTask();

    CustomTaskResponse fetchAllTask(int pageNo, int pageSize);

    TaskResponse fetchTaskById(Long id);

    TaskResponse createSingleTask(TaskRequest taskRequest);

    TaskResponse updateTask(Long id, TaskRequest taskRequest);

    String deleteTask(Long id);

    TaskResponse completeTask(Long id);

    TaskResponse inCompleteTask(Long id);
}
