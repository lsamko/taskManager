package com.example.demo.service.impl;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.TaskWithNameAlreadyExistsException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
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
        if (ids != null && !ids.isEmpty()) {
            return taskRepository.findTasksByTaskIdIn(ids);
        }
        return Collections.emptyList();
    }

    @Override
    public TaskResponseDto findById(String uuid) {
        return taskRepository.findTaskByTaskId(uuid)
            .orElseThrow(() -> new TaskNotFoundException("Could not find task: " + uuid));
    }

    @Override
    public void deleteById(String uuid) {
        taskRepository.deleteTaskById(uuid);
    }

    @Override
    public TaskResponseDto updateById(String uuid, TaskUpdateDto taskUpdateDto) {
        TaskResponseDto toUpdate = this.findById(uuid);
        String newName = taskUpdateDto.getName();
        if (this.isNameChanged(toUpdate, newName) && this.isTaskWithNameExists(newName)) {
            throw new TaskWithNameAlreadyExistsException(
                String.format("Task with name '%s' already exists", newName)
            );
        }
        toUpdate.setName(taskUpdateDto.getName());
        return taskMapper.fromEntityToResponseDto(toUpdate);
    }

    @Override
    public List<TaskResponseDto> getUsersTask(String userId) {
        List<Task> tasksByUsers = taskRepository.findTasksByUserId(userId);

        return tasksByUsers.stream()
            .map(taskMapper::fromEntityToResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public boolean isNameChanged(TaskResponseDto toUpdate, String taskName) {
        return !toUpdate.getName().equals(taskName);
    }

    @Override
    public boolean isTaskWithNameExists(String name) {
        Task probe = new Task();
        probe.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name", GenericPropertyMatcher::exact)
            .withIgnorePaths("taskId");

        Example<Task> example = Example.of(probe, matcher);

        return this.taskRepository.count(example) > 0;
    }
}
