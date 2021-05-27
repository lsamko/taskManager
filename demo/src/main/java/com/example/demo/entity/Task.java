package com.example.demo.entity;

import com.example.demo.dto.TaskResponseDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data

public class Task extends TaskResponseDto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer priority;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String taskId;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime dueToDate;

    @Column
    private String userId;
}


