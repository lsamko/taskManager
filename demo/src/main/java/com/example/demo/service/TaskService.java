package com.example.demo.service;

import com.example.demo.Task;
import com.example.demo.dto.TaskRequestDto;
import java.util.Collection;

public interface TaskService {

   String createTask(TaskRequestDto taskRequestDto);

  Collection<Task> getAllTasks(Integer from, Integer size);

   Task getTaskById(String uuid);

   void deleteTaskById(String uuid);
}
