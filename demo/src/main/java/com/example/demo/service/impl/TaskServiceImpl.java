package com.example.demo.service.impl;

import com.example.demo.converter.CronExpressionConverter;
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
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.quartz.CronExpression;
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
    private final CronExpressionConverter converter;

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
        LocalDateTime startDay = date.atStartOfDay();
        LocalDateTime endDay = date.atTime(23, 59, 59);
        return taskRepository.findTaskByDueDateBetweenAndDone(startDay, endDay, false, Sort.by("priority"));
    }

    @Override
    public void rescheduleTasks(LocalDate date) {
        LocalDateTime startDay = date.atStartOfDay();
        int dayOfYear = date.getDayOfYear();

        List<Task> toUpdate = taskRepository.findTaskByDueDateLessThan(startDay);
        toUpdate.forEach(task -> {
            Date getCron = getNextExecution(task.getCron());
            LocalDateTime nextSchedulerDate = convertToLocalDateTimeViaInstant(getCron);
            task.setDueDate(nextSchedulerDate);
        });
        taskRepository.saveAll(toUpdate);
    }

    public static Date getNextExecution(CronExpression cronExpression) {
        CronExpression cron = new CronExpression(cronExpression);
        return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }
}
