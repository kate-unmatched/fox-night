package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@FieldNameConstants
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity{
    @NotNull
    private String lastName;
    @NotNull
    private String firstName;

    private String patronymic;
    @NotNull
    private String login;
    @JsonIgnore
    @Column(name = "password")
    private String passwordHash;
    @NotNull
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @NotNull
    private LocalDateTime lastAuthorization;



}
