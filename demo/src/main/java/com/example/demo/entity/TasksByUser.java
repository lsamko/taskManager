package com.example.demo.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks_by_user")
@NoArgsConstructor
@Data
public class TasksByUser {

    @Id
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;
}
