package com.example.demo.service.impl;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
private final TaskRepository taskRepository;


    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {

        return null;
    }


    @Override
    public List<TaskResponseDto> getAllTasks(Integer from, Integer size) {

        return null;
    }
}
