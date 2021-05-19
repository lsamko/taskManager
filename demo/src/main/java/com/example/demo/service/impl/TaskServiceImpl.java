package com.example.demo.service.impl;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = taskMapper.fromRequestDtoToEntity(taskRequestDto);
        Task taskToSave = taskRepository.save(task);
        return taskMapper.fromEntityToResponseDto(taskToSave);
    }

    @Override
    public List<TaskResponseDto> getAllTasks(Integer from, Integer size) {
        Pageable paging = PageRequest.of(from, size);
        Page<Task> pageResult = taskRepository.findAll(paging);
        return taskMapper.fromEntityListToResponseDtoList(pageResult.getContent());
    }

    @Override
    public List<Task> findTasksByIds(List<String> ids) {
        if(ids != null && !ids.isEmpty()) {
            return taskRepository.findTasksByTaskIdIn(ids);
        } return Collections.emptyList();
    }

    @Override
    public Optional<Task> findById(String uuid) {
        return taskRepository.findTaskByTaskId(uuid);
    }

    @Override
    public void deleteById(String uuid) {
        taskRepository.deleteTaskById(uuid);
    }

    @Override
    public TaskResponseDto updateById(String uuid, TaskUpdateDto taskUpdateDto) {
        Optional<Task> toUpdate = this.findById(uuid);
        Task task = toUpdate.orElseThrow(() -> new NoSuchElementException());
        task.setName(taskUpdateDto.getName());
        return taskMapper.fromEntityToResponseDto(task);
    }

    @Override
    public List<TaskResponseDto> getUsersTask(String userId) {
        List<Task> tasksByUsers = taskRepository.findTasksByUserId(userId);

       return tasksByUsers.stream()
            .map(taskMapper::fromEntityToResponseDto)
            .collect(Collectors.toList());
    }
}
