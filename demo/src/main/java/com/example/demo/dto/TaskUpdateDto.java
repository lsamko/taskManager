package com.example.demo.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TaskUpdateDto {

    @NotNull(message = "Task name can not be null")
    private String name;

    @NotNull
    Integer priority;

    @NotNull
    LocalDateTime dueToDate;

    private boolean done;

    public TaskUpdateDto() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueToDate() {
        return dueToDate;
    }

    public void setDueToDate(LocalDateTime dueToDate) {
        this.dueToDate = dueToDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public TaskUpdateDto name(String name) {
        this.name = name;
        return this;
    }
}
