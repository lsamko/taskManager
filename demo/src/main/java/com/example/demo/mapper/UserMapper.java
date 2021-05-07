package com.example.demo.mapper;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper()
public interface UserMapper {
    User fromRequestDtoToEntity(UserRequestDto userRequestDto);
    UserResponseDto fromEntityToResponseDto(User user);

    List<UserResponseDto> fromEntityListToResponseDtoList(List<User> users);
}
