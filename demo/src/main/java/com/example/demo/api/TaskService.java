package com.example.demo.api;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entity.Task;
import java.util.List;


public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    List<TaskResponseDto> getAllTasks(Integer from, Integer size);

    List<Task> findTasksByIds(List<String> ids);

    TaskResponseDto findById(String uuid);

    void deleteById(String uuid);

    TaskResponseDto updateById(String uuid, TaskUpdateDto taskUpdateDto);


    List<TaskResponseDto> getUsersTask(String userId);


     boolean isNameChanged(TaskResponseDto toUpdate, String taskName);

     boolean isTaskWithNameExists(String name);
}
