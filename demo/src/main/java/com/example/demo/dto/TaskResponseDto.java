package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskResponseDto {

    private String name;
    private Boolean done;
    private Integer priority;
    private String uuid;
    private LocalDateTime dueToDate;
}
