package com.example.demo.dto;

import com.example.demo.entity.Task;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class UserRequestDto {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String userId;

    private List<Task> tasks= new ArrayList<>();
}
