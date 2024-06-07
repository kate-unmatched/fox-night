package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@FieldNameConstants
@Table(name = "users_")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RestAudit extends IdentityEntity<Long> {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestTime;


}
