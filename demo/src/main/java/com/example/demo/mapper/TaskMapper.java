package com.example.demo.mapper;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.entity.Task;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "taskId", expression = "java(java.util.UUID.randomUUID().toString())")
    Task fromRequestDtoToEntity(TaskRequestDto taskRequestDto);
    TaskResponseDto fromEntityToResponseDto(Task task);

    List<TaskResponseDto> fromEntityListToResponseDtoList(List<Task> tasks);
}
