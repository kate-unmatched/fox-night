package com.tsp.foxnight.dto;

import com.tsp.foxnight.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserAllDTO {
    private Long id;

    private String name;

    private String photo;
}
