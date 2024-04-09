package com.tsp.foxnight;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}