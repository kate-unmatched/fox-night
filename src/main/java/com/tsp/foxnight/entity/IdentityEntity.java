package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;


@Data
@MappedSuperclass
@FieldNameConstants
public abstract class IdentityEntity<V>{

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private V id;
}
