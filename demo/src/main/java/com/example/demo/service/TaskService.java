package com.example.demo.service;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import java.util.List;
import org.springframework.stereotype.Service;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

   List<TaskResponseDto> getAllTasks(Integer from, Integer size);
}
