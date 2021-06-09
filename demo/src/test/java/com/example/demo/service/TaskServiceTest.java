package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.entity.Task;
import com.example.demo.exception.TaskWithNameAlreadyExistsException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.TaskMapperImpl;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.TaskServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private static final String TASK_NAME = "learn English";
    private static final int TASK_PRIORITY = 1;
    private static final LocalDateTime TASK_DUE_TO_DO = LocalDateTime.of(2021, 10, 9, 12, 00);
    @Mock
    private TaskRepository taskRepository;

    private TaskMapper taskMapper = new TaskMapperImpl();
    private TaskServiceImpl taskServiceImpl;
    private TaskService taskService;
    private TaskRequestDto taskRequestDto;
    private UserRepository userRepository;
    private Task task;

    @BeforeEach
    public void setUp() {
        taskServiceImpl = new TaskServiceImpl(taskRepository, taskMapper, userRepository);
        taskRequestDto = new TaskRequestDto();
        taskRequestDto.setPriority(TASK_PRIORITY);
        taskRequestDto.setName(TASK_NAME);
        taskRequestDto.setDueToDate(TASK_DUE_TO_DO);
        taskRequestDto.setDone(false);

        task = new Task();
        task.setName(TASK_NAME);
        task.setDueToDate(TASK_DUE_TO_DO);
        task.setPriority(TASK_PRIORITY);
       // task.setDone(false);
    }

    @Test
    public void createTask() {
        when(taskRepository.existsTaskByName(TASK_NAME)).thenReturn(false);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        taskServiceImpl.createTask(taskRequestDto);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        Task captureTask = taskCaptor.getValue();
        assertNull(captureTask.getId());
        assertNotNull(captureTask.getTaskId());
        assertEquals(captureTask.getName(),TASK_NAME);
        assertEquals(captureTask.getDueToDate(), TASK_DUE_TO_DO);
        assertEquals(captureTask.getPriority(), TASK_PRIORITY);
    }
}
