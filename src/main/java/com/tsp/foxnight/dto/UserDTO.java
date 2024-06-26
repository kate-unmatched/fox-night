package com.tsp.foxnight.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsp.foxnight.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.persistence.Transient;
import java.time.LocalDate;
@Data
public class UserDTO {
    @NotBlank
    private String name;
    private LocalDate birthday;
    private LocalDate startWork;
    private String telegram;
    @NotBlank
    private String city;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String login;
    @Transient
    private String password;

    @NotNull
    private UserRole role;

    private String photo;

}
