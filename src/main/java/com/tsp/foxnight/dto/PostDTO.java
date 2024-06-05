package com.tsp.foxnight.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDTO {
    @NotBlank
    private String heading;
    @NotBlank
    private String description;
}