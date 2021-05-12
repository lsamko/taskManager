package com.example.demo.dto;

import com.example.demo.entity.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class UserUpdateDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String userId;

    private List<String> taskIds = new ArrayList<>();
}
