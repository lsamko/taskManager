package com.example.demo.dto;

import com.example.demo.entity.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.CronExpression;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String userId;
    private List<Task> tasks= new ArrayList<>();
    private String notification;
}
