package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    Optional<User> findById(String uuid);

    void deleteById(String uuid);

    UserResponseDto updateById(String uuid, UserUpdateDto userUpdateDto);

    List<String> getUsersTask(String userId);
}
