package com.example.demo.entity;

import com.example.demo.dto.UserResponseDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapping;

@Entity
@NoArgsConstructor
@Data
public class User extends UserResponseDto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(nullable = false, unique = true, length = 255)
    private String firstName;

    @Basic
    @Column(nullable = false, length = 255)
    private String lastName;

    @Basic
    @Column(unique = true, nullable = false, length = 255)
    private String userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

}
