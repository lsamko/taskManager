package com.example.demo.api.impl;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWithNameAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.api.TaskService;
import com.example.demo.api.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
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
    public UserResponseDto findById(String uuid) {
        return userRepository.findUserByUserId(uuid)
            .orElseThrow(() -> new UserNotFoundException("Could not find user: " + uuid));

    }


    @Override
    public void deleteById(String uuid) {
        userRepository.deleteUserByUserId(uuid);
    }

    @Override
    public UserResponseDto updateById(String uuid, UserUpdateDto userUpdateDto) {
        List<Task> tasks = taskService.findTasksByIds(userUpdateDto.getTaskIds());
        UserResponseDto toUpdate = this.findById(uuid);
        String newLastName = userUpdateDto.getLastName();
        if (this.isNameChanged(toUpdate, newLastName) && this.isUserWithNameExists(newLastName)) {
            throw new UserWithNameAlreadyExistsException(
                String.format("User with lastname '%s' already exists", newLastName)
            );
        }
        toUpdate.setLastName(userUpdateDto.getLastName());
        return userMapper.fromEntityToResponseDto(toUpdate);
    }

    @Override
    public boolean isNameChanged(UserResponseDto toUpdate, String userName) {
        return !toUpdate.getLastName().equals(userName);
    }

    @Override
    public boolean isUserWithNameExists(String lastName) {
        User probe = new User();
        probe.setLastName(lastName);

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("lastName", GenericPropertyMatcher::exact)
            .withIgnorePaths("userId");

        Example<User> example = Example.of(probe, matcher);

        return this.userRepository.count(example) > 0;

    }
}