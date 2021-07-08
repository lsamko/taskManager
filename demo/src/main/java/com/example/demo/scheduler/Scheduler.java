package com.example.demo.scheduler;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final TaskService taskService;

    @Scheduled(cron = "0/10 * * * * *")
    void processTodaysTasks() {
        List<Task> tasks = taskService.findTask(LocalDate.now());
        tasks
            .stream().map(task -> String.format("name: %s, priority: %s", task.getName(), task.getPriority()))
            .forEach(LOGGER::info);
    }


    @Scheduled(cron = "0/10 * * * * *")
    void reschedule() {
        List<Task> tasks = taskService.findTasksToBeRescheduled(LocalDate.now());
        LocalDate localDate = LocalDate.now();
        LocalDateTime dueDate = localDate.atStartOfDay();
        List<Task> tasksForToday = taskService.findTask(LocalDate.now());

        //calculate new dueDate
        //set this date to task
        // save this tasks in taskService

    }


//    void processTodaysTasksByName() {
//        List<Task> tasks = taskService.findTask(LocalDate.now());
//            List<String> listOfTasksNames = tasks.stream()
//                .map(Task::getName)
//                .collect(Collectors.toList());
//            listOfTasksNames.forEach(System.out::println);
//        }
//
//
//    void processTodaysTasksSortedByPriority() {
//        List<Task> tasks = taskService.findTask(LocalDate.now());
//        List<Task> listOfTasksNames = tasks.stream()
//            .sorted(Comparator.comparingInt((Task::getPriority)))
//            .collect(Collectors.toList());
//        listOfTasksNames.forEach(System.out::println);
//
//    }
//
//    void processTodaysTasksSortedByPriorityAndPrintAllInfo() {
//        List<Task> tasks = taskService.findTask(LocalDate.now());
//
//        Map<Integer, List<Task>> listOfTasksNames = tasks.stream()
//            .collect(Collectors.groupingBy(Task::getPriority));
//        listOfTasksNames
//            .forEach((priority, t) -> System.out.format("priority %s: %s\n", priority, t));
//    }

    //        List<Task> tasks = taskService.findTask(LocalDate.now());
    //        Map<Integer, String> map = tasks
    //            .stream()
    //            .collect(Collectors.toMap(
    //                Task::getPriority,
    //                Task::getName,
    //                (name1, name2) -> name1 + "; " + name2));
    //
    //        System.out.println(map);
    //
    //    }
}
