package com.example.demo.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.CronExpression;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDto {

    @NotNull(message = "Task name can not be null")
    private String name;

    @NotNull
    Integer priority;

    @NotNull
    LocalDateTime dueDate;

    private boolean done;
    private CronExpression cron;

    public TaskUpdateDto name(String name) {
        this.name = name;
        return this;
    }
}
