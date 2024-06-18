package com.tsp.foxnight.dto;

import com.tsp.foxnight.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.io.File;
import java.time.LocalDate;

@Data
public class UserCreateDTO {
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

}
