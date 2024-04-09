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
    private V id;
}
