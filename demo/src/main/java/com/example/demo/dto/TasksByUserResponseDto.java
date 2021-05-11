package com.example.demo.dto;

import com.example.demo.entity.Task;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksByUserResponseDto {
    private String userId;
    private List<Task> tasks= new ArrayList<>();
}
