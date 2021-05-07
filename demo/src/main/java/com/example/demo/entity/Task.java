package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "om.Task")
@Table(name = "OM_TASK")
@NoArgsConstructor
@Data
public class Task extends BaseEntity implements Serializable {
    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "UUID", unique = true)
    private String uuid;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime dueToDate;

    @ManyToMany
    @JoinTable(name = "USER_TASK", joinColumns = @JoinColumn(name = "UUID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users = new HashSet<>();
}


