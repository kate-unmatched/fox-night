package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@FieldNameConstants
@Table(name = "users_")
@RequiredArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User extends AbstractEntity<Long>{

    @NotBlank
    private String name;
    private LocalDate birthday;
    private LocalDate startWork;
    private String telegram;
    private String city;
    @Email
    private String email;
    private String phoneNumber;

    @NotBlank
    @Column(updatable = false)
    private String login;
    @JsonIgnore
    @Column(name = "password")
    private String passwordHash;

    private Boolean isActive;
    @NotNull
    private Long roleId;

}
