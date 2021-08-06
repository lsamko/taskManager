package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String notification;

    public UserUpdateDto update(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        return this;
    }

}
