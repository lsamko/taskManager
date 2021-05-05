package com.example.demo.mapper;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.entity.Task;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper()
public interface TaskMapper {
    Task fromRequestDtoToEntity(TaskRequestDto taskRequestDto);
    TaskResponseDto fromEntityToResponseDto(Task task);

    List<TaskResponseDto> fromEntityListToResponseDtoList(List<Task> tasks);
}
