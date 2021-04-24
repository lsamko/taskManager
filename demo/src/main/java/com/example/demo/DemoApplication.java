package com.example.demo;

import com.example.demo.repository.TaskRepository;
import java.util.HashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        HashMap<Integer, String> createTask = new HashMap<>();
        createTask.put(1, "Wake up!");
        createTask.put(2, "Take a shower");
        createTask.put(3, "Prepare a breakfast ");
        createTask.put(4, "Choose a close");
        createTask.put(5, "Go at work");
        System.out.println(createTask);
    }
}
