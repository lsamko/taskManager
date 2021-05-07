package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Task")
@Table(name = "TASK")
@NoArgsConstructor
@Data

public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "UUID", unique = true)
    private String uuid;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime dueToDate;

}


