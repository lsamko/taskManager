package com.example.demo.service.impl;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWithNameAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto, List<Task> tasks) {
        User user = userMapper.fromRequestDtoToEntity(userRequestDto);
        user.setTasks(tasks);
        String newLastName = userRequestDto.getLastName();
        String newFirstName = userRequestDto.getFirstName();
        if (this.isUserWithLastNameExists(newLastName)) {
            throw new UserWithNameAlreadyExistsException(
                String.format("User with lastname '%s' already exists", newLastName)
            );
        } else if (this.isUserWithFirstNameExists(newFirstName)) {
            throw new UserWithNameAlreadyExistsException(
                String.format("User with firstname '%s' already exists", newFirstName)
            );
        }
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
        User user = userRepository.findUserByUserId(uuid)
            .orElseThrow(() -> new UserNotFoundException("Could not find user: " + uuid));
        return userMapper.fromEntityToResponseDto(user);
    }

    @Override
    public void deleteById(String uuid) {
        userRepository.deleteUserByUserId(uuid);
    }

    @Override
    public UserResponseDto updateById(String uuid, UserUpdateDto userUpdateDto) {
        User toUpdate = userRepository.findUserByUserId(uuid)
            .orElseThrow(() -> new UserNotFoundException("Could not find user: " + uuid));
        String newLastName = userUpdateDto.getLastName();
        String newFirstName = userUpdateDto.getFirstName();
        if (this.isNameChanged(toUpdate, newLastName) && this.isUserWithLastNameExists(newLastName)) {
            throw new UserWithNameAlreadyExistsException(
                String.format("User with lastname '%s' already exists", newLastName)
            );
        } else if (this.isUserWithFirstNameExists(newFirstName)) {
            throw new UserWithNameAlreadyExistsException(
                String.format("User with firstname '%s' already exists", newFirstName)
            );
        }
        toUpdate.setLastName(userUpdateDto.getLastName());
        toUpdate.setFirstName(userUpdateDto.getFirstName());
        return userMapper.fromEntityToResponseDto(toUpdate);
    }

    private boolean isNameChanged(User toUpdate, String userName) {
        return !toUpdate.getLastName().equals(userName);
    }

    @Override
    public boolean isUserWithLastNameExists(String lastName) {
        return userRepository.existsUserByLastName(lastName);
    }

    @Override
    public boolean isUserWithFirstNameExists(String firstName) {
        return userRepository.existsUserByFirstName(firstName);
    }

    @Override
    public boolean existsByUserId(String uuid) {
        return userRepository.existsByUserId(uuid);
    }

}