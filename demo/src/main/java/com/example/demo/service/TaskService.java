package com.example.demo.service;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entity.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    List<TaskResponseDto> getAllTasks(Integer from, Integer size);

    Optional<Task> findById(UUID uuid);

    void deleteById(UUID uuid);

    TaskResponseDto updateById(UUID uuid, TaskUpdateDto taskUpdateDto);
}
