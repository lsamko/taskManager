package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    Optional<User> findById(UUID uuid);

    void deleteById(UUID uuid);

    UserResponseDto updateById(UUID uuid, UserUpdateDto userUpdateDto);
}
