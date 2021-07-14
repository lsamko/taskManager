package com.example.demo.scheduler;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import com.example.demo.twilio.MessageSenderService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final TaskService taskService;
    private final MessageSenderService messageSenderService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Scheduled(cron = "${task-manager.job.tasks.cron}")
    void processTodaysTasks() {
        log.debug("Task processing...");
        List<Task> tasks = taskService.findTask(LocalDate.now());
        List<String> taskDescriptions = tasks
            .stream()
            .map(task -> String.format("name: %s, date: %s", task.getName(), task.getDueDate().format(formatter)))
            .collect(
                Collectors.toList());
        String message = "Your tasks for today: " + StringUtils.join(taskDescriptions, ',');
        messageSenderService.sendTasks(message);
    }

    @Scheduled(cron = "${task-manager.job.tasks.reschedule.cron}")
    void reschedule() {
        log.debug("Task reschedule started...");
        taskService.rescheduleTasks(LocalDate.now());
    }
}
