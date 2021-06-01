package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto findById(String uuid);

    void deleteById(String uuid);

    UserResponseDto updateById(String uuid, UserUpdateDto userUpdateDto);

    boolean isNameChanged(UserResponseDto toUpdate, String userName);

    boolean isUserWithLastNameExists(String lastName);
}
