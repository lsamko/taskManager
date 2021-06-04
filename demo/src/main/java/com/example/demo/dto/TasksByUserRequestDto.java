package com.example.demo.dto;

import com.example.demo.entity.Task;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksByUserRequestDto {

    @NotNull
    private String userId;
    @Max(100)
    private List<Task> tasks = new ArrayList<>();
}
