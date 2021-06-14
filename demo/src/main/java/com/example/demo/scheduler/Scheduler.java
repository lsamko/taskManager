package com.example.demo.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(cron = "0 0 8 * * *")
     void wakeUp()  {
        LOGGER.info("Time to wake up! " + new Date());
    }

    @Scheduled(cron = "0 0 17 * * Mon,Wed,Fri")
    void doWorkout()  {
        LOGGER.info("Time to exercise! " + new Date());
    }

    @Scheduled(cron = "0 07 15 * * Wed")
    void learnSpring()  {
        LOGGER.info("Time to learn! " + new Date());
    }
}
