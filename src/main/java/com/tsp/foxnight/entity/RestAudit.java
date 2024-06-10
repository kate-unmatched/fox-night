package com.tsp.foxnight.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.tsp.foxnight.utils.JsonNodeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@FieldNameConstants
@Table(name = "rest_audit")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RestAudit extends IdentityEntity<Long> {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestTime;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private RestType requestType;

    @Convert(converter = JsonNodeConverter.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode body;
}