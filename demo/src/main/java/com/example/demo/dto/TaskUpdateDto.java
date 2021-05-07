package com.example.demo.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class TaskUpdateDto {
    @NotNull(message = "Task name can not be null")
    private String name;

    @NotNull
    Integer priority;

    @NotNull
    LocalDateTime dueToDate;

    private Boolean done;


}
