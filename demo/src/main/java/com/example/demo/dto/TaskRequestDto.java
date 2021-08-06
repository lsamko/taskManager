package com.example.demo.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.CronExpression;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@NoArgsConstructor
public class TaskRequestDto {

    @NotNull(message = "Task name can not be null")
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Min(1)
    @Max(5)
    Integer priority;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE)
    LocalDateTime dueDate;

    private Boolean done;

    private CronExpression cron;
}
