package com.example.demo.service;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.entity.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

   TaskResponseDto createTask(TaskRequestDto taskRequestDto);

  List<TaskResponseDto> getAllTasks(Integer from, Integer size);

   Optional<Task> getTaskById(String uuid);

   void deleteTaskById(String uuid);
}
