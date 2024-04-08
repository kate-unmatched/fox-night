package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@Entity
@FieldNameConstants
@Table(name = "users_")
@ToString(exclude = "roles")
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity<Long>{
    @NotBlank
    private String name;

    @Email
    private String email;
    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Column(updatable = false)
    private String login;
    @JsonIgnore
    @Column(name = "password")
    private String passwordHash;

    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
}
