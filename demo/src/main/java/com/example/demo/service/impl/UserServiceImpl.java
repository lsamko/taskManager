package com.example.demo.service.impl;

import com.example.demo.dto.TaskResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.TasksByUser;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskService taskService;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        List<Task> tasks = taskService.findTasksByIds(userRequestDto.getTaskIds());
        User user = userMapper.fromRequestDtoToEntity(userRequestDto);
        user.setTasks(tasks);
        User userToSave = userRepository.save(user);
        return userMapper.fromEntityToResponseDto(userToSave);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.fromEntityListToResponseDtoList(users);
    }

    @Override
    public Optional<User> findById(String uuid) {
         return userRepository.findUserByUserId(uuid);

    }


    @Override
    public void deleteById(String uuid) {
        userRepository.deleteUserByUserId(uuid);
    }

    @Override
    public UserResponseDto updateById(String uuid, UserUpdateDto userUpdateDto) {
        List<Task> tasks = taskService.findTasksByIds(userUpdateDto.getTaskIds());
        Optional<User> toUpdate = findById(uuid);
        User user = toUpdate.orElseThrow(() -> new NoSuchElementException());
        user.setTasks(tasks);
        userRepository.save(user);
        return userMapper.fromEntityToResponseDto(user);
    }


}
