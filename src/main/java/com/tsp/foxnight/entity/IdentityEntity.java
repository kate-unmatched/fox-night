package com.tsp.foxnight.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;


@Data
@MappedSuperclass
@FieldNameConstants
public abstract class IdentityEntity<V> {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private V id;
}
