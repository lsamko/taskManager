package com.example.demo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import lombok.Getter;

/**
 * Base entity that contains general fields (id) for other ones.
 *
 */
@MappedSuperclass
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "OM_INCREMENT_SEQ_GENERATOR",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "OM_INCREMENT_SEQ_GENERATOR",
            sequenceName = "OM_INCREMENT_SEQ_GENERATOR", allocationSize = 1)
    private long id;

}
