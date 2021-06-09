package com.example.demo.scheduler;

import com.example.demo.entity.Task;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(cron="0 0 8 * * ?")
    public void myScheduler(){

        System.out.println("Schedule the job!");
    }
}
