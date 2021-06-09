package com.example.demo.entity;

import com.example.demo.dto.TaskResponseDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NoArgsConstructor
@Data

public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(nullable = false, length = 16)
    private Integer priority;

    @Basic
    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Basic
    @Column(unique = true, nullable = false, length = 255)
    private String taskId;

    @Basic
    @CreatedDate
    @Column(name = "DATE", nullable = false)
    private LocalDateTime dueToDate;

    @Column
    private String userId;
}


