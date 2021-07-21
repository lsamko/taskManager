package com.example.demo.service;

import static com.spotify.hamcrest.pojo.IsPojo.pojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
import com.example.demo.service.impl.TaskServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Captor
    ArgumentCaptor<Task> taskCaptor;
    @Captor
    ArgumentCaptor<String> idCaptor;
    @Mock
    private TaskRepository taskRepository;

    private TaskRequestDto taskRequestDto;

    private final TaskMapper taskMapper = new TaskMapperImpl();

    private TaskServiceImpl taskServiceImpl;
    private UserTasksFacade userTasksFacade;
    private String id = "008";

    @BeforeEach
    public void setUp() {
        taskServiceImpl = new TaskServiceImpl(taskRepository, taskMapper);
        taskRequestDto = new TaskRequestDto();
        taskRequestDto.setPriority(TASK_PRIORITY);
        taskRequestDto.setName(TASK_NAME);
        taskRequestDto.setDueDate(TASK_DUE_TO_DO);
        taskRequestDto.setDone(false);

        Task task = new Task();
        task.setName(TASK_NAME);
        task.setDueDate(TASK_DUE_TO_DO);
        task.setPriority(TASK_PRIORITY);
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.existsTaskByName(TASK_NAME)).thenReturn(false);
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
        when(taskRepository.findTaskByTaskId(idCaptor.capture())).thenReturn(Optional.of(TASK_A));
        taskServiceImpl.findById(id);
        String value = idCaptor.getValue();
        assertThat(value, is(equalTo(id)));
    }


    @Test
    public void testFindByUuidShouldThrowExceptionWhenEntityNotFound() {
        String id = "005";
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
    void findAllTasks() {
        int size = 2;
        int from = 1;
        Page<Task> page = createTasks();

        when(taskRepository.findAll(PageRequest.of(from, size))).thenReturn(page);
        List<TaskResponseDto> tasks = taskServiceImpl.findAll(from, size);

        assertEquals(4, tasks.size());

        //TODO replace with Stream
        for (int i = 0; i < tasks.size(); i++) {
            assertTaskResponseDTO(page.getContent().get(i), tasks.get(i));
        }
    }

    @Test
    void testUpdateTaskById() {
        when(taskRepository.existsTaskByName("Hello")).thenReturn(false);
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


    @Test
    void testDeleteTaskById() {
        when(taskRepository.deleteTaskByTaskId(id)).thenReturn(Optional.of(TASK_A));
        TaskResponseDto taskResponseDto = taskServiceImpl.deleteById(id);
        assertTaskResponseDTO(TASK_A, taskResponseDto);
    }

    @Test
    void testDeleteTaskByIdNotFound() {
        when(taskRepository.deleteTaskByTaskId(id)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.deleteById(id), "Could not find task: " + id);
    }

    @Test
    void getUsersTask() {
    }

    @Test
    void findTask() {
        LocalDate localDate = LocalDate.now();
        LocalDateTime startDay = localDate.atStartOfDay();
        LocalDateTime endDay = localDate.atTime(23, 59, 59);

        taskServiceImpl.findTask(localDate);
        verify(taskRepository).findTaskByDueDateBetweenAndDoneNot(startDay, endDay, false, Sort.by("priority"));
    }

    @Test
    void rescheduleTasks() {
    }


    private Page<Task> createTasks() {
        List<Task> list = new ArrayList<>();
        list.add(new Task(1, "Task1", "005", TASK_DUE_TO_DO));
        list.add(new Task(2, "Task2", "09", TASK_DUE_TO_DO));
        list.add(new Task(5, "Task3", "11", TASK_DUE_TO_DO));
        list.add(new Task(1, "Task4", "002", TASK_DUE_TO_DO));
        return new PageImpl<>(list);
    }

    private void assertTaskResponseDTO(Task task, TaskResponseDto taskResponseDto) {
        assertEquals(task.getName(), taskResponseDto.getName());
        assertEquals(task.getTaskId(), taskResponseDto.getTaskId());
        assertEquals(task.getPriority(), taskResponseDto.getPriority());
        assertEquals(task.getDueDate(), taskResponseDto.getDueDate());
    }


}
