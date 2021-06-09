package com.example.demo.service;

import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.Task;
import com.example.demo.exception.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTasksFacade {

    private final TaskService taskService;
    private final UserService userService;

    public List<TaskResponseDto> getUsersTask(String userId) {
        if (!userService.existsByUserId(userId)) {
            throw new UserNotFoundException(String.format("User with id '%s' not found", userId));
        }

        return taskService.getUsersTask(userId);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        List<Task> tasks = taskService.findTasksByIds(userRequestDto.getTaskIds());
        return userService.createUser(userRequestDto,tasks);
    }
}
