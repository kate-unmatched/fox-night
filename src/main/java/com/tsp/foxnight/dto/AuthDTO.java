package com.tsp.foxnight.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}