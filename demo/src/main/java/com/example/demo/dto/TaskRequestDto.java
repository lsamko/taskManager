package com.example.demo.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskRequestDto {

    @NotNull(message = "Task name can not be null")
    private String name;

    @NotNull(message = "Task number can not be null")
    private Integer number;

    private Boolean done;
}
