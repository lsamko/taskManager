package com.example.demo.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskRequestDto {

    @NotNull(message = "Task name can not be null")
    private String name;

    @NotNull
    Integer priority;

    @NotNull
    LocalDateTime dueToDate;

    private Boolean done;
}
