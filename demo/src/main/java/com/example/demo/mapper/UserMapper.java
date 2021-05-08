package com.example.demo.mapper;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userUuid", expression = "java(java.util.UUID.randomUUID().toString())")
    User fromRequestDtoToEntity(UserRequestDto userRequestDto);
    UserResponseDto fromEntityToResponseDto(User user);

    List<UserResponseDto> fromEntityListToResponseDtoList(List<User> users);
}
