package com.tsp.foxnight.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UserOfficerDTO {
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
    private String login;

    private Boolean isActive;

}
