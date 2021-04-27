package com.example.demo.repository;

import com.example.demo.Task;
import com.example.demo.dto.TaskRequestDto;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    private Task task;
    Map<String, Task> taskMap = new HashMap<>();

    public void deleteTaskById(String uuid) {
        taskMap.remove(uuid);
    }


    public Collection<Task> getTasks(Pageable pageable) {
        return taskMap.values();
    }

    public Task saveTask(Task task) {
        taskMap.put(task.getUuid(), task);
        return task;
    }

    public Task getTaskById(String uuid) {
        return taskMap.get(uuid);
    }

}
