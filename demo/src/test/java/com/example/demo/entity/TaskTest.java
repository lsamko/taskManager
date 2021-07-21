package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

    Task task;

    @BeforeEach
    public void setUp(){
        task = new Task();
    }
    @Test
    void getId() {
        Long idValue = 4L;
        task.setId(idValue);
        assertEquals (idValue, task.getId());
    }

    @Test
    void getPriority() {
        Integer priorityValue = 2;
        task.setPriority(priorityValue);
        assertEquals(priorityValue, task.getPriority());
    }

    @Test
    void getName() {
        String nameValue = "My task";
        task.setName(nameValue);
        assertEquals(nameValue, task.getName());
    }

    @Test
    void getTaskId() {
        String taskIdValue = "01";
        task.setTaskId(taskIdValue);
        assertEquals(taskIdValue, task.getTaskId());
    }

    @Test
    void getDueDate() {
        LocalDateTime dueDateValue = LocalDateTime.of(2021, 10, 9, 12, 00);
        task.setDueDate(dueDateValue);
        assertEquals(dueDateValue, task.getDueDate());
    }

    @Test
    void getUserId() {
        String userIdValue = "100";
        task.setUserId(userIdValue);
        assertEquals(userIdValue, task.getUserId());
    }

    @Test
    void getDone() {
       Boolean doneValue = false;
        task.setDone(doneValue);
        assertEquals(doneValue, task.getDone());
    }
}