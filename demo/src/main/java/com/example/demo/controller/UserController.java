package com.example.demo.controller;

import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.service.UserService;
import com.example.demo.service.UserTasksFacade;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserTasksFacade userTasksFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userTasksFacade.createUser(userRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers(@RequestParam(value = "from", defaultValue = "0") @Min(0) @Valid Integer from,
        @RequestParam(value = "size", defaultValue = "10")  @Min(10) @Max(100000) @Valid Integer size)  {
        return userService.getAllUsers(from,  size);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserById(@PathVariable String uuid) {
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

    @GetMapping("/{userId}/tasks")
    @ResponseStatus(HttpStatus.OK)
    List<TaskResponseDto> getUsersTask(@PathVariable String userId){

        return userTasksFacade.getUsersTask(userId);
    }

}
