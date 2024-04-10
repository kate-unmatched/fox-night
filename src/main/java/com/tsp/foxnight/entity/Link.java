package com.tsp.foxnight.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Link extends IdentityEntity<Long>{
    @NotBlank
    private String name;

    @NotBlank
    private String url;

}
