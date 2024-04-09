package com.tsp.foxnight.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Post extends AbstractEntity<Long>{
    @NotBlank
    private String heading;
    @NotBlank
    private String description;
    @Lob
    private byte[] fileData;
    @NotNull
    private Long userId;

}
