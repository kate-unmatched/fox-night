package com.tsp.foxnight.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AuditEmployee extends IdentityEntity<Long>{
    @NotNull
    private Long userId;
    @NotNull
    private Long userRole;
    private LocalDateTime time;
    @NotBlank
    private String typeRest;
    private JsonNode meta;

}
