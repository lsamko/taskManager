package com.example.demo.scheduler;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import com.example.demo.twilio.MessageSenderService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final TaskService taskService;
    private final MessageSenderService messageSenderService;


    @Scheduled(cron = "${spring.batch.job.tasks.cron}")
    void processTodaysTasks() {
        List<Task> tasks = taskService.findTask(LocalDate.now());
        List<String> taskDescriptions = tasks
            .stream().map(task -> String.format("name: %s, date: %s", task.getName(), task.getDueDate())).collect(
                Collectors.toList());
        String message = "Your tasks for today: " + StringUtils.join(taskDescriptions, ',');
        messageSenderService.sendTasks(message);
    }

    @Scheduled(cron = "${spring.batch.job.tasks.reschedule.cron}")
    void reschedule() {
        taskService.rescheduleTasks(LocalDate.now());
    }
}
