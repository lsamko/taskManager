package com.example.demo.dto;

import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.CronExpression;

@Data
@NoArgsConstructor
public class TaskResponseDto {

    private String name;
    private Boolean done;
    private Integer priority;
    private String taskId;
    private LocalDateTime dueDate;
    private String userId;
    private CronExpression cron;
}
