package com.example.demo.repository;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.entity.Task;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Map<String, Task> taskMap = new HashMap<>();

    Optional<Task> getTaskById(String uuid);
    void deleteTaskById(String uuid);

}
