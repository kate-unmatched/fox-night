package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "info_company")
@Accessors(chain = true)
public class InfoCompany{
    @Id
    private Long id;
    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime modificationDate;
    @NotBlank
    private String heading;
    @NotBlank
    private String description;
    @ElementCollection
    private List<String> photos;

    @PreUpdate
    public void preUpdate(){
        modificationDate = LocalDateTime.now();
    }
}
