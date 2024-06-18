package com.tsp.foxnight.dto;

import com.tsp.foxnight.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.persistence.Transient;
import java.time.LocalDate;

@Data
public class UserUpdateDTO {
    private String name;
    private LocalDate birthday;
    private LocalDate startWork;
    private String telegram;
    private String city;
    private String email;
    private String phoneNumber;
}
