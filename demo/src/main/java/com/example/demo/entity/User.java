package com.example.demo.entity;

import com.example.demo.dto.UserResponseDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

}
