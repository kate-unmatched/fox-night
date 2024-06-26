package com.tsp.foxnight.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rest_audit")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RestAudit extends IdentityEntity<Long> {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestTime;

    @NotNull
    private String name;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private RestType requestType;

    private String body;

    @PrePersist
    public void prePersist(){
        requestTime = LocalDateTime.now();
    }
}