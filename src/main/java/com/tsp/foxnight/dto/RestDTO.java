package com.tsp.foxnight.dto;

import com.tsp.foxnight.entity.RestType;
import com.tsp.foxnight.entity.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RestDTO {
    private Long id;

    private LocalDateTime requestTime;

    private String name;

    private String role;

    private String requestType;

    private String body;

}
