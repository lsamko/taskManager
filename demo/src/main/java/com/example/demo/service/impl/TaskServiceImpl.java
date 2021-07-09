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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = taskMapper.fromRequestDtoToEntity(taskRequestDto);
        String taskName = taskRequestDto.getName();
        if (this.isTaskWithNameExists(taskName)) {
            throw new TaskWithNameAlreadyExistsException(
                String.format("Task with name '%s' already exists", taskName)
            );
        }
        Task taskToSave = taskRepository.save(task);
        return taskMapper.fromEntityToResponseDto(taskToSave);
    }

    @Override
    public List<TaskResponseDto> findAll(Integer from, Integer size) {
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
        Task task = taskRepository.findTaskByTaskId(uuid)
            .orElseThrow(() -> new TaskNotFoundException("Could not find task: " + uuid));
        return taskMapper.fromEntityToResponseDto(task);
    }

    @Override
    public TaskResponseDto deleteById(String uuid) {
        Task task = taskRepository.deleteTaskByTaskId(uuid)
            .orElseThrow(() -> new TaskNotFoundException("Could not find task: " + uuid));
        return taskMapper.fromEntityToResponseDto(task);
    }

    @Override
    public TaskResponseDto updateById(String uuid, TaskUpdateDto taskUpdateDto) {
        Task toUpdate = taskRepository.findTaskByTaskId(uuid)
            .orElseThrow(() -> new TaskNotFoundException("Could not find task: " + uuid));
        String newName = taskUpdateDto.getName();
        if (this.isNameChanged(toUpdate, newName) && this.isTaskWithNameExists(newName)) {
            throw new TaskWithNameAlreadyExistsException(
                String.format("Task with name '%s' already exists", newName)
            );
        }
        toUpdate.setName(taskUpdateDto.getName());
        taskRepository.save(toUpdate);
        return taskMapper.fromEntityToResponseDto(toUpdate);
    }

    @Override
    public List<TaskResponseDto> getUsersTask(String userId) {
        List<Task> tasksByUsers = taskRepository.findTasksByUserId(userId);
        return tasksByUsers.stream()
            .map(taskMapper::fromEntityToResponseDto)
            .collect(Collectors.toList());
    }

    private boolean isNameChanged(Task toUpdate, String taskName) {
        return !toUpdate.getName().equals(taskName);
    }

    @Override
    public boolean isTaskWithNameExists(String name) {
        return taskRepository.existsTaskByName(name);
    }

    @Override
    public List<Task> findTask(LocalDate date) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime startDay = localDate.atStartOfDay();
        LocalDateTime endDay = localDate.atTime(23, 59, 59);
        //LocalDateTime endDay = localDate.atStartOfDay().plusDays(1).minusSeconds(1);
        return taskRepository.findTaskByDueToDateBetweenAndDoneNot(startDay, endDay, false, Sort.by("priority"));
    }

    @Override
    public void rescheduleTasks(LocalDate date) {

        LocalDate localDate = LocalDate.now();
        LocalDateTime startDay = localDate.atStartOfDay();

        List<Task> toUpdate = taskRepository.findTaskByDueToDateLessThan(startDay);
        for (Task task:toUpdate) {
            LocalDateTime currentDate = task.getDueToDate().plusDays(1);
            task.setDueToDate(currentDate);
        }

        taskRepository.saveAll(toUpdate);
    }

}
