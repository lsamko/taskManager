package com.example.demo;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Task {

    Integer priority;
    String name;
    String uuid;
    LocalDateTime dueToDate;


}
