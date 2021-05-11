package com.example.demo.controller;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.User;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUserById(@PathVariable String uuid) {
        return userService.findById(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteUserById(@PathVariable String uuid) {
        userService.deleteById(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUserById(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable String uuid) {
        return userService.updateById(uuid, userUpdateDto);
    }

    @GetMapping("/{userId/tasks}")
    @ResponseStatus(HttpStatus.OK)
    List<String> getUsersTask(@PathVariable String userId){

        return userService.getUsersTask(userId);
    }
}
