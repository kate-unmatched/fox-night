package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "links")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Link extends IdentityEntity<Long> {

    @CreatedDate
    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestTime;

    private String shortName;

    @NotBlank
    private String url;

    @PrePersist
    public void prePersist(){
        requestTime = LocalDateTime.now();
    }
}
