package com.example.demo.repository;

import java.util.HashMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    HashMap<Integer, String> createTask = new HashMap<>();

}
