package com.example.demo.service;

import static com.spotify.hamcrest.pojo.IsPojo.pojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.TaskWithNameAlreadyExistsException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.TaskMapperImpl;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.TaskServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private static final String TASK_NAME = "learn English";
    private static final int TASK_PRIORITY = 1;
    private static final LocalDateTime TASK_DUE_TO_DO = LocalDateTime.of(2021, 10, 9, 12, 00);


    private final Task TASK_A = new Task(
        2,
        "Go fishing",
        "008",
        TASK_DUE_TO_DO
    );


    @Mock
    private TaskRepository taskRepository;

    private TaskMapper taskMapper = new TaskMapperImpl();
    private TaskServiceImpl taskServiceImpl;
    private TaskUpdateDto taskUpdateDto;
    private TaskRequestDto taskRequestDto;
    private UserRepository userRepository;
    private Task task;

    @BeforeEach
    public void setUp() {
        taskServiceImpl = new TaskServiceImpl(taskRepository, taskMapper);
        taskRequestDto = new TaskRequestDto();
        taskRequestDto.setPriority(TASK_PRIORITY);
        taskRequestDto.setName(TASK_NAME);
        taskRequestDto.setDueDate(TASK_DUE_TO_DO);
        taskRequestDto.setDone(false);

        task = new Task();
        task.setName(TASK_NAME);
        task.setDueDate(TASK_DUE_TO_DO);
        task.setPriority(TASK_PRIORITY);
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.existsTaskByName(TASK_NAME)).thenReturn(false);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        taskServiceImpl.createTask(taskRequestDto);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        Task captureTask = taskCaptor.getValue();
        assertNull(captureTask.getId());
        assertNotNull(captureTask.getTaskId());
        assertEquals(captureTask.getName(), TASK_NAME);
        assertEquals(captureTask.getDueDate(), TASK_DUE_TO_DO);
        assertEquals(captureTask.getPriority(), TASK_PRIORITY);
    }

    @Test
    public void testCreateTaskWithExistingName() {
        Executable tryCreate = () -> taskServiceImpl.createTask(taskRequestDto);
        when(taskRepository.existsTaskByName(TASK_NAME)).thenReturn(true);
        TaskWithNameAlreadyExistsException exc = assertThrows(TaskWithNameAlreadyExistsException.class, tryCreate);
        assertThat(exc, is(
            pojo(Exception.class)
                .where(Exception::getMessage,
                    is(equalTo("Task with name '" + TASK_NAME + "' already exists")))
        ));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testFindTaskByUuid() {
        String id = "008";
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        when(taskRepository.findTaskByTaskId(idCaptor.capture())).thenReturn(Optional.of(TASK_A));
        taskServiceImpl.findById(id);
        String value = idCaptor.getValue();
        assertThat(value, is(equalTo(id)));
    }

    @Test
    public void testFindByUuidShouldThrowExceptionWhenEntityNotFound() {
        String id = "005";
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        when(taskRepository.findTaskByTaskId(idCaptor.capture())).thenReturn(Optional.empty());
        Executable tryFindById = () -> taskServiceImpl.findById(id);
        TaskNotFoundException exc = assertThrows(TaskNotFoundException.class, tryFindById);

        assertThat(exc, is(
            pojo(Exception.class)
                .where(Exception::getMessage,
                    is(equalTo("Could not find task: " + id)))
        ));

        String value = idCaptor.getValue();
        assertThat(value, is(equalTo(id)));
    }

    @Test
    void testUpdateTask() {
        String id = "008";
        when(taskRepository.existsTaskByName("Hello")).thenReturn(false);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        when(taskRepository.findTaskByTaskId(id)).thenReturn(Optional.of(TASK_A));
        TaskResponseDto taskResponse = taskServiceImpl.updateById(id, new TaskUpdateDto().name("Hello"));

        assertThat(taskResponse.getName(), is("Hello"));

        verify(taskRepository).save(taskCaptor.capture());

        Task task = taskCaptor.getValue();
        assertThat(task, is(
            pojo(Task.class)
                .where(Task::getName, is("Hello"))
                .where(Task::getTaskId, is(id))
        ));
    }
}
