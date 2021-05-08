package com.example.demo.service.impl;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.fromRequestDtoToEntity(userRequestDto);
        User userToSave = userRepository.save(user);
        return userMapper.fromEntityToResponseDto(userToSave);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.fromEntityListToResponseDtoList(users);
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return userRepository.findUserById(uuid);
    }

    @Override
    public void deleteById(UUID uuid) {
        userRepository.deleteUserById(uuid);
    }

    @Override
    public UserResponseDto updateById(UUID uuid, UserUpdateDto userUpdateDto) {
        Optional<User> toUpdate = findById(uuid);
        User user = toUpdate.orElseThrow(() -> new NoSuchElementException());
        user.setTasks((Set<Task>) userUpdateDto.getTasks());
        return userMapper.fromEntityToResponseDto(user);
    }

}
