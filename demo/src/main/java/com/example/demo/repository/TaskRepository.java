package com.example.demo.repository;

import com.example.demo.entity.Task;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskByTaskId(String uuid);
    void deleteTaskById(String uuid);

    List<Task> findTasksByTaskIdIn(List<String> ids);
}
