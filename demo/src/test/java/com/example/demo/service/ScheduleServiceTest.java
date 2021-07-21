package com.example.demo.service;

import static com.spotify.hamcrest.pojo.IsPojo.pojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.dto.TaskRequestDto;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.TaskMapperImpl;
import com.example.demo.repository.TaskRepository;
import com.example.demo.scheduler.JobScheduler;
import com.example.demo.service.impl.TaskServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskRequestDto taskRequestDto;
    @Mock
    JobScheduler jobScheduler;
    private final TaskMapper taskMapper = new TaskMapperImpl();

    private TaskServiceImpl taskServiceImpl;
    private static final LocalDate LOCAL_DATE = LocalDate.now();
    private static final LocalDateTime START_DAY = LOCAL_DATE.atStartOfDay();
    private static final LocalDateTime TASK_DUE_TO_DO = LOCAL_DATE.atTime(23, 59, 59);
    private final Task TASK_A = new Task(
        2,
        "Go fishing",
        "008",
        TASK_DUE_TO_DO
    );

    @Test
    void testProcessTodaysTasks() {
        when(taskRepository.findTaskByDueDateBetweenAndDoneNot(START_DAY, TASK_DUE_TO_DO, false, Sort.by("priority")))
            .thenReturn(createTasks());
        taskServiceImpl.findTask(LOCAL_DATE);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        Task task = taskCaptor.getValue();
        assertThat(task, is(
            pojo(Task.class)
                .where(Task::getDueDate, is(START_DAY))
        ));

    }



    private List<Task> createTasks() {
        List<Task> list = new ArrayList<>();
        list.add(new Task(1, "Task1", "005", TASK_DUE_TO_DO));
        list.add(new Task(2, "Task2", "09", TASK_DUE_TO_DO));
        list.add(new Task(5, "Task3", "11", TASK_DUE_TO_DO));
        list.add(new Task(1, "Task4", "002", TASK_DUE_TO_DO));
        return list;
    }
}
