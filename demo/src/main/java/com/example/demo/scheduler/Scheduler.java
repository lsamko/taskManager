package com.example.demo.scheduler;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import java.time.LocalDate;
import java.util.Date;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final TaskService taskService;

    @Scheduled(cron = "0/10 * * * * *")
    void processTodaysTasks() {
       List<Task> tasks = taskService.findTask(LocalDate.now());
        LOGGER.info("Your tasks are: " + tasks);
    }

}
