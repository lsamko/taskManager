package com.example.demo.service.impl;

import com.example.demo.Task;
import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public String createTask(TaskRequestDto taskRequestDto) {
        final String uuid = UUID.randomUUID().toString();
        final String taskName = taskRequestDto.getName();
        final Integer priority = taskRequestDto.getPriority();
        final LocalDateTime dueToDate = taskRequestDto.getDueToDate();
        final Task toSave = new Task(priority, taskName, uuid, dueToDate);
        return taskRepository.saveTask(toSave).getUuid();
    }


    @Override
    public Collection<Task> getAllTasks(Integer from, Integer size) {
        Pageable paging = PageRequest.of(from, size);

        return taskRepository.getTasks(paging);
    }

    @Override
    public Task getTaskById(String uuid) {

        return taskRepository.getTaskById(uuid);
    }

    @Override
    public void deleteTaskById(String uuid) {
        taskRepository.deleteTaskById(uuid);
    }

}
