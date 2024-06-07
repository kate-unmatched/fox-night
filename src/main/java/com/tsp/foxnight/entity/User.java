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

import java.time.LocalDate;

@Data
@Entity
@FieldNameConstants
@Table(name = "users_")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity<Long> {

    @NotBlank
    private String name;
    private LocalDate birthday;
    private LocalDate startWork;
    private String telegram;
    @NotBlank
    private String city;
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Column(updatable = false)
    private String login;
    @JsonIgnore
    private String password;

    private Boolean isActive;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private String photo;
}
