package com.example.demo.controller;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.service.TaskService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        return taskService.createTask(taskRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponseDto> getAllTasks(@RequestParam(value = "from", defaultValue = "0") @Min(0) @Valid Integer from,
        @RequestParam(value = "size", defaultValue = "10")  @Min(10) @Max(100000) @Valid Integer size) {
        return taskService.getAllTasks(from, size);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto getTaskById(@PathVariable String uuid) {
        return taskService.findById(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteTaskById(@PathVariable String uuid) {
        taskService.deleteById(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto updateTask(@Valid @RequestBody TaskUpdateDto taskUpdateDto, @PathVariable String uuid) {
        return taskService.updateById(uuid, taskUpdateDto);
    }

}
