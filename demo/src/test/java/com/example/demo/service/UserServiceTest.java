package com.example.demo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void createUser() {

    }

    @Test
    void getAllUsers(){
        User user = new User();
        List<User> userData = new ArrayList();
        userData.add(user);
        when(userRepository.findAll()).thenReturn(userData);
        List<UserResponseDto> users = userService.getAllUsers();
        assertEquals(users.size(),0);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void isUserWithLastNameExists() {
    }

    @Test
    void isUserWithFirstNameExists() {
    }

    @Test
    void existsByUserId() {
    }
}