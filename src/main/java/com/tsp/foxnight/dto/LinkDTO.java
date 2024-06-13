package com.tsp.foxnight.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsp.foxnight.entity.IdentityEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
public class LinkDTO{
    private String shortName;

    @NotBlank
    private String url;
}
