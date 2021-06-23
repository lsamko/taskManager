package com.example.demo.service;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entity.Task;
import java.time.LocalDate;
import java.util.List;


public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    List<TaskResponseDto> findAll(Integer from, Integer size);

    List<Task> findTasksByIds(List<String> ids);

    TaskResponseDto findById(String uuid);

    TaskResponseDto deleteById(String uuid);

    TaskResponseDto updateById(String uuid, TaskUpdateDto taskUpdateDto);

    List<TaskResponseDto> getUsersTask(String userId);

    boolean isTaskWithNameExists(String name);

    List<Task> findTask(LocalDate date);
}
