package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.Task;
import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto, List<Task> tasks);

    List<UserResponseDto> getAllUsers(Integer from, Integer size);

    UserResponseDto findById(String uuid);

    void deleteById(String uuid);

    UserResponseDto updateById(String uuid, UserUpdateDto userUpdateDto);

    boolean existsByUserId(String uuid);

}
